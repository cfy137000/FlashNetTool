package com.lanou.chenfengyao.flashnet.image.cache;

import android.content.Context;
import android.graphics.Bitmap;


/**
 * Created by ChenFengYao on 16/5/21.
 * 包括内存缓存和硬盘缓存
 */
public class TribleCache implements BitmapCache {
    private MemoryCache memoryCache;
    private DiskCache diskCache;
    //二级内存缓存
    private SecondMemoryCache secondMemoryCache;

    public TribleCache(Context mContext) {
        memoryCache = new MemoryCache() {
            //移除方法
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue != null) {
                    secondMemoryCache.putBitmap(key, oldValue);
                }
                super.entryRemoved(evicted, key, oldValue, newValue);
            }
        };
        diskCache = new DiskCache(mContext);
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
            bitmap = secondMemoryCache.getBitmap(url);
            if (bitmap == null) {
                bitmap = diskCache.getBitmap(url);
            }
        }
        return bitmap;
    }
}
