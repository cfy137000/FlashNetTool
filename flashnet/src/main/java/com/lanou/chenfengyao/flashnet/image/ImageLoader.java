package com.lanou.chenfengyao.flashnet.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.netengine.EngineFactory;
import com.lanou.chenfengyao.flashnet.netengine.NetEngine;
import com.lanou.chenfengyao.flashnet.netengine.OkHttpUtil;
import com.lanou.chenfengyao.flashnet.corepool.CoreSingleThreadPool;
import com.lanou.chenfengyao.flashnet.image.cache.DoubleCache;
import com.lanou.chenfengyao.flashnet.netengine.Response;
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
    private NetEngine netEngine;

    //主线程的回调Handler
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            //在主线程获得Bitmap等信息
        }
    };


    private ImageLoader(Context context) {
        threadPool = CoreSingleThreadPool.getInstance();
        doubleCache = new DoubleCache(context);
        //获取默认的网络加载引擎
        netEngine = EngineFactory.getDefaultEngine();
    }

    //单例的入口方法
    public static ImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoader(context);
                }
            }
        }
        return imageLoader;
    }



}
