package com.lanou.chenfengyao.flashnet.image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.lanou.chenfengyao.flashnet.image.cache.DiskCache;
import com.lanou.chenfengyao.flashnet.image.cache.DoubleMemoryCache;
import com.lanou.chenfengyao.flashnet.interfaces.NetListener;
import com.lanou.chenfengyao.flashnet.netengine.connection.HttpTool;

/**
 * Created by ChenFengYao on 16/8/1.
 * 用来获取Bitmap的
 */
public class BitmapRunnable implements Runnable {
    private String url;
    private Handler handler;
    private DoubleMemoryCache doubleMemoryCache;
    private DiskCache diskCache;
    private NetListener<Bitmap> bitmapNetListener;

    public BitmapRunnable(String url, Handler handler, DoubleMemoryCache doubleMemoryCache, DiskCache diskCache, NetListener<Bitmap> bitmapNetListener) {
        this.url = url;
        this.handler = handler;
        this.doubleMemoryCache = doubleMemoryCache;
        this.diskCache = diskCache;
        this.bitmapNetListener = bitmapNetListener;
    }

    @Override
    public void run() {
        Bitmap bitmap = doubleMemoryCache.getBitmap(url);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(url);
        }
        if (bitmap == null) {
            HttpTool httpTool = new HttpTool();
            bitmap = httpTool.getBitmap(url, -1, -1);
            if (bitmap != null) {
                doubleMemoryCache.putBitmap(url, bitmap);
                // diskCache.putBitmap(url, bitmap);
            }
        }
        ImageLoader.Result result = new ImageLoader.Result();
        result.bitmap = bitmap;
        result.tag = url;
        result.bitmapNetListener = bitmapNetListener;
        Message message = handler.obtainMessage();
        message.what = 2;
        message.obj = result;

        handler.sendMessage(message);

    }
}
