package com.lanou.chenfengyao.flashnet.image.cache;

import android.content.Context;
import android.graphics.Bitmap;


/**
 * Created by ChenFengYao on 16/5/21.
 * 包括内存缓存和硬盘缓存
 */
public class DoubleMemoryCache implements BitmapCache {
    private MemoryCache memoryCache;

    //二级内存缓存
    private SecondMemoryCache secondMemoryCache;

    public DoubleMemoryCache(Context mContext) {
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
        secondMemoryCache = new SecondMemoryCache();
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if(bitmap==null||url==null){
            return;
        }
        memoryCache.put(url, bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap == null) {
            bitmap = secondMemoryCache.getBitmap(url);
        }
        return bitmap;
    }
}
