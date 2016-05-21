package com.lanou.chenfengyao.flashnet.image.cache;

import android.graphics.Bitmap;

/**
 * Created by ChenFengYao on 16/5/21.
 * 包括内存缓存和硬盘缓存
 */
public class DoubleCache implements BitmapCache {
    private MemoryCache memoryCache;
    private DiskCache diskCache;

    public DoubleCache() {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache();
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.putBitmap(url, bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.getBitmap(url);
        }
        return bitmap;
    }
}
