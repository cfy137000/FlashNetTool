package com.lanou.chenfengyao.flashnet.image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.image.cache.DiskCache;
import com.lanou.chenfengyao.flashnet.image.cache.DoubleMemoryCache;
import com.lanou.chenfengyao.flashnet.netengine.connection.HttpTool;

/**
 * Created by ChenFengYao on 16/7/18.
 */
public class ImgRunnable implements Runnable {
    private String url;
    private int reqW, reqH;
    private ImageView imageView;
    private Handler handler;
    private DoubleMemoryCache doubleMemoryCache;
    private DiskCache diskCache;

    public ImgRunnable(DoubleMemoryCache doubleMemoryCache,
                       DiskCache diskCache, ImageView imageView, int reqH, int reqW, String url, Handler handler) {
        this.imageView = imageView;
        this.reqH = reqH;
        this.reqW = reqW;
        this.url = url;
        this.handler = handler;
        this.doubleMemoryCache = doubleMemoryCache;
        this.diskCache = diskCache;
    }

    public ImgRunnable(DoubleMemoryCache doubleMemoryCache,
                       DiskCache diskCache,
                       ImageView imageView, String url, Handler handler) {

        this(doubleMemoryCache, diskCache, imageView, -1, -1, url, handler);
    }

    @Override
    public void run() {
        if (!url.equals((String) imageView.getTag())) {
            return;
        }
        Bitmap bitmap = doubleMemoryCache.getBitmap(url);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(url);
        }
        if (bitmap == null) {
            HttpTool httpTool = new HttpTool();
            bitmap = httpTool.getBitmap(url, reqW, reqH);
            if(bitmap!=null) {
                doubleMemoryCache.putBitmap(url, bitmap);
                diskCache.putBitmap(url, bitmap);
            }
        }
        ImageLoader.Result result = new ImageLoader.Result();
        result.bitmap = bitmap;
        result.imageView = imageView;
        result.tag = url;
        Message message = handler.obtainMessage();
        message.what = 1;
        message.obj = result;
        handler.sendMessage(message);

    }
}
