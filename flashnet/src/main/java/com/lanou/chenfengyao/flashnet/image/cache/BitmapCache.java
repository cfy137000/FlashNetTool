package com.lanou.chenfengyao.flashnet.image.cache;

import android.graphics.Bitmap;

/**
 * Created by ChenFengYao on 16/5/21.
 */
public interface BitmapCache {
    void putBitmap(String url, Bitmap bitmap);
    Bitmap getBitmap(String url);
}
