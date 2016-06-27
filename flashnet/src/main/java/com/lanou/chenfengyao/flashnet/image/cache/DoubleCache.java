package com.lanou.chenfengyao.flashnet.image.cache;

import android.content.Context;
import android.graphics.Bitmap;


/**
 * Created by ChenFengYao on 16/5/21.
 * 包括内存缓存和硬盘缓存
 */
public class DoubleCache implements BitmapCache {
    private MemoryCache memoryCache;
    private DiskCache diskCache;
    //硬盘缓存的最小容量
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    //是否有硬盘缓存
    private boolean mIsDiskLruCacheCreated = false;

    public DoubleCache(Context mContext) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(mContext);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.putBitmap(url,bitmap);
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
