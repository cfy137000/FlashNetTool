package com.lanou.chenfengyao.flashnet.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.netengine.OkHttpUtil;
import com.lanou.chenfengyao.flashnet.corepool.CoreSingleThreadPool;
import com.lanou.chenfengyao.flashnet.image.cache.DoubleCache;
import com.lanou.chenfengyao.flashnet.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ChenFengYao on 16/5/21.
 * 网络请求的核心
 * 图片的异步加载
 * 图片的压缩
 */
public class ImageLoader {
    private CoreSingleThreadPool threadPool;
    private DoubleCache doubleCache;
    private static ImageLoader imageLoader;
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            //在主线程获得Bitmap等信息
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            Log.d("ImageLoader", "handler");

        }
    };


    private ImageLoader() {
        threadPool = CoreSingleThreadPool.getInstance();
        doubleCache = new DoubleCache();
    }

    //单例的入口方法
    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoader();
                }
            }
        }
        return imageLoader;
    }

    //直接把图片绑定到ImageView上
    public void bindBitmap(final String url, final ImageView imageView, int reqWidth, int reqHeight) {
        Log.d("ImageLoader", "bind");
        final Bitmap bitmap = doubleCache.getBitmap(url);
        if (bitmap != null) {
            //TODO 加上缩放
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Log.d("ImageLoader","run");
                Bitmap newBitmap = downloadBitmapFromURL(url);
                if (newBitmap != null) {
                    LoaderResult result = new LoaderResult(imageView,url,newBitmap);
                    mMainHandler.obtainMessage(Values.NET_SUCCESS,result)
                            .sendToTarget();
                }
                Log.d("ImageLoader", "bitmap==null:" + (bitmap == null));
            }
        };
        threadPool.execute(loadBitmapTask);
    }

    /**
     * 从网络拉取Bitmap
     *
     * @param URL 网址
     * @return 拉取的Bitmap, 如果返回null证明没有拉取陈宫
     */
    private Bitmap downloadBitmapFromURL(String URL) {
        Log.d("ImageLoader", "down");
        Bitmap bitmap = null;
        //获得返回的输入流
        InputStream inputStream = null;
        try {
            inputStream = OkHttpUtil.getInstance().getSync(URL).body().byteStream();
            Log.d("ImageLoader", "inputStream==null:" + (inputStream == null));
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(inputStream);
        }
            Log.d("ImageLoader", "bitmap==null:" + (bitmap == null));

        return bitmap;
    }

    private static class LoaderResult{
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
