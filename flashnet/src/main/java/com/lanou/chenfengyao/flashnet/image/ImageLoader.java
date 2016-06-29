package com.lanou.chenfengyao.flashnet.image;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lanou.chenfengyao.flashnet.netengine.EngineFactory;
import com.lanou.chenfengyao.flashnet.netengine.NetEngine;
import com.lanou.chenfengyao.flashnet.corepool.CoreSingleThreadPool;
import com.lanou.chenfengyao.flashnet.image.cache.TribleCache;

/**
 * Created by ChenFengYao on 16/5/21.
 * 网络请求的核心
 * 图片的异步加载
 * 图片的压缩
 * ImageLoader不能单例,可以有多个ImageLoader
 */
public class ImageLoader {
    private CoreSingleThreadPool threadPool;
    private TribleCache tribleCache;
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
        tribleCache = new TribleCache(context);
        //获取默认的网络加载引擎
        netEngine = EngineFactory.getDefaultEngine();
    }

    //入口方法
    public static ImageLoader getInstance(Context context) {
        return new ImageLoader(context);
    }

}
