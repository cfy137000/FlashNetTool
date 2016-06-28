package com.lanou.chenfengyao.flashnet.image.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.lanou.chenfengyao.flashnet.Option;

//LruCache 只需要指定最大内存和每一个图片所使用的内存即可使用
public class MemoryCache extends LruCache<String, Bitmap> {

    public MemoryCache() {
        super(Option.getInstance().getMemoryMaxKB());
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }


}
