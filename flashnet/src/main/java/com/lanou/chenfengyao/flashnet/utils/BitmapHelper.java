/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lanou.chenfengyao.flashnet.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * 可以根据传进来的大小动态的压缩图片
 */

public class BitmapHelper {

    // 允许InputStream最大允许 mark/reset的值.
    public static final int MAX_READ_LIMIT_PER_IMG = 1024 * 1024;

    public static Bitmap scaleBitmap(Bitmap src, int maxWidth, int maxHeight) {
        double scaleFactor = Math.min(
                ((double) maxWidth) / src.getWidth(), ((double) maxHeight) / src.getHeight());
        return Bitmap.createScaledBitmap(src,
                (int) (src.getWidth() * scaleFactor), (int) (src.getHeight() * scaleFactor), false);
    }

    public static Bitmap scaleBitmap(int scaleFactor, InputStream is) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(is, null, bmOptions);
    }

    public static int findScaleFactor(int targetW, int targetH, InputStream is) {
        Log.d("BitmapHelper", "targetW:" + targetW);
        if (targetW < 0 && targetH < 0) {
            return 1;
        }
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bmOptions);
        int actualW = bmOptions.outWidth;
        int actualH = bmOptions.outHeight;

        return Math.min(actualW / targetW, actualH / targetH);
    }

    public static BitmapInfo getReqInfo(ImageView imageView) {
        BitmapInfo bitmapInfo = new BitmapInfo();
        Context context = imageView.getContext();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        //宽
        if (imageView.getWidth() > 0) {
            bitmapInfo.width = imageView.getWidth();
        } else if (imageView.getLayoutParams().width > 0) {
            bitmapInfo.width = imageView.getLayoutParams().width;
        } else {
            bitmapInfo.width = displayMetrics.widthPixels;
        }
        //高
        if (imageView.getHeight() > 0) {
            bitmapInfo.height = imageView.getHeight();
        } else if (imageView.getLayoutParams().height > 0) {
            bitmapInfo.height = imageView.getLayoutParams().height;
        } else {
            bitmapInfo.height = displayMetrics.heightPixels;
        }

        return bitmapInfo;
    }

    public static class BitmapInfo {
        public int width;
        public int height;

        public BitmapInfo() {
            width = -1;
            height = -1;
        }
    }
}
