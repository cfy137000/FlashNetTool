package com.lanou.chenfengyao.flashnet.image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.netengine.connection.HttpTool;

/**
 * Created by ChenFengYao on 16/7/18.
 */
public class ImgRunnable implements Runnable {
    private String url;
    private int reqW,reqH;
    private ImageView imageView;
    private Handler handler;

    public ImgRunnable(ImageView imageView, int reqH, int reqW, String url,Handler handler) {
        this.imageView = imageView;
        this.reqH = reqH;
        this.reqW = reqW;
        this.url = url;
        this.handler = handler;
    }

    public ImgRunnable(ImageView imageView, String url,Handler handler) {

        this(imageView,-1,-1,url,handler);
    }

    @Override
    public void run() {
        HttpTool httpTool = new HttpTool();
        if(!url.equals((String) imageView.getTag())){
            return;
        }
        Bitmap bitmap = httpTool.getBitmap(url,reqW,reqH);
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
