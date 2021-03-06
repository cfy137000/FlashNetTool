package com.lanou.chenfengyao.flashnet.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.image.cache.DiskCache;
import com.lanou.chenfengyao.flashnet.interfaces.NetListener;
import com.lanou.chenfengyao.flashnet.netengine.EngineFactory;
import com.lanou.chenfengyao.flashnet.netengine.NetEngine;
import com.lanou.chenfengyao.flashnet.corepool.CoreSingleThreadPool;
import com.lanou.chenfengyao.flashnet.image.cache.DoubleMemoryCache;
import com.lanou.chenfengyao.flashnet.utils.BitmapHelper;

/**
 * Created by ChenFengYao on 16/5/21.
 * 网络请求的核心
 * 图片的异步加载
 * 图片的压缩
 * ImageLoader不能单例,可以有多个ImageLoader
 */
public class ImageLoader {
    private CoreSingleThreadPool threadPool;
    //二级内存缓存
    private DoubleMemoryCache doubleMemoryCache;
    //硬盘缓存
    private DiskCache diskCache;
    private static ImageLoader imageLoader;
    private NetEngine netEngine;
    public static final int TAG_KEY = 0x01;


    //主线程的回调Handler
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //在主线程获得Bitmap等信息
            switch (msg.what) {
                case 1:
                    Result result = (Result) msg.obj;
                    if (result.imageView.getTag().equals(result.tag)) {
                        result.imageView.setImageBitmap(result.bitmap);
                    }
                    break;
                case 2:
                    Result bitmapResult = (Result) msg.obj;
                    NetListener<Bitmap> bitmapNetListener
                             = bitmapResult.bitmapNetListener;
                    bitmapNetListener.onNetSuccess(bitmapResult.bitmap);
            }
        }
    };

    //获得ImageView的方法
    public void getImg(String url, ImageView imageView, int defaultId) {
        imageView.setTag(url);
        if (defaultId > 0) {
            imageView.setImageResource(defaultId);
        } else {
            imageView.setImageBitmap(null);
        }
        BitmapHelper.BitmapInfo bitmapInfo = BitmapHelper.getReqInfo(imageView);
        ImgRunnable runnable = new ImgRunnable(doubleMemoryCache, diskCache, imageView, bitmapInfo.height, bitmapInfo.width, url, mMainHandler);
        threadPool.execute(runnable);
    }

    //获得Bitmap的方法
    public void getImageBitmap(String url, NetListener<Bitmap> bitmapNetListener){
        BitmapRunnable bitmapRunnable = new BitmapRunnable(url,mMainHandler,doubleMemoryCache,diskCache,bitmapNetListener);
        threadPool.execute(bitmapRunnable);
    }


    private ImageLoader(Context context) {
        threadPool = CoreSingleThreadPool.getInstance();
        //初始化缓存
        doubleMemoryCache = new DoubleMemoryCache(context);
        diskCache = new DiskCache(context);
        //获取默认的网络加载引擎
        netEngine = EngineFactory.getDefaultEngine();
    }

    //入口方法
    public static ImageLoader getInstance(Context context) {
        return new ImageLoader(context);
    }

    public static class Result {
        ImageView imageView;
        Bitmap bitmap;
        String tag;
        NetListener<Bitmap> bitmapNetListener;
    }

}
