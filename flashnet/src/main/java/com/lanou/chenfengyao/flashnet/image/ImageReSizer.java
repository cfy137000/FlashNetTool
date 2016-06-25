package com.lanou.chenfengyao.flashnet.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by ChenFengYao on 16/5/21.
 * 用来实现压缩图片的类
 */
public class ImageReSizer {

    private static final String TAG = "ImageResizer";

    /**
     * 加载Resources内图片的压缩方法
     *
     * @param resource
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromResource(Resources resource, int resId, int reqWidth, int reqHeight) {
        //第一步，将inJustDecodeBounds设置为true并加载图片
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource, resId, options);

        //第二步，calculateInSampleSize方法中获取bitmap原始宽高信息，
        // 第三步，根据需求的大小获取inSampleSize采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //第四步，将inJustDecodeBounds设置为false，重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource, resId, options);
    }

    /**
     * 加载文件中的图片的压缩方法
     *
     * @param fileDescriptor
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
        //依然是上个方法中的四步
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * 计算inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        //获取图片的宽高
        final int width = options.outWidth;
        final int height = options.outHeight;
        //初始化inSampleSize
        int inSampleSize = 1;

        //计算最大inSampleSize值是2的幂，同时保持width,height大于reqWidth,reHeight
        if (height > reqHeight || width > reqWidth) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        Log.d(TAG, "inSampleSize:" + inSampleSize);
        return inSampleSize;
    }
}
