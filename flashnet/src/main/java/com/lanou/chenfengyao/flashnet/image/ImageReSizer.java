package com.lanou.chenfengyao.flashnet.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by ChenFengYao on 16/5/21.
 * 用来实现压缩图片的类
 */
public class ImageReSizer {

    public Bitmap decodeSampledBitmapFromFile(FileDescriptor fd, int reqW, int reqH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, reqW, reqH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    //用来根据要求的压缩比例来计算图片输出的宽高
    public int calculateInSampleSize(BitmapFactory.Options options, int reqW, int reqH) {
        //如果有0的话 不压缩
        if (reqH * reqW == 0) {
            return 1;
        }
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqH || width > reqW) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqH
                    && (halfWidth / inSampleSize) >= reqW) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
