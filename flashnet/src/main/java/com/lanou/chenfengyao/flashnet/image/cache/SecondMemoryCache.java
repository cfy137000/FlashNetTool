package com.lanou.chenfengyao.flashnet.image.cache;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ChenFengYao on 16/6/28.
 * 内存缓存的二级缓存
 * 利用软引用来实现图片缓存,没有固定的大小限制
 */
public class SecondMemoryCache implements BitmapCache{
    private ConcurrentHashMap<String ,SoftReference<Bitmap>> hashMap;

    public SecondMemoryCache() {
        hashMap = new ConcurrentHashMap<>();
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        bitmap = null;//置空强引用
        hashMap.put(url,softReference);
    }

    @Override
    public Bitmap getBitmap(String url) {
        SoftReference<Bitmap> softReference = hashMap.get(url);
        if(softReference == null){
            return null;
        }
        if(softReference.get()!=null){
            //找到了图片,把它返回
            return softReference.get();
        }
        //如果软引用已经被释放了,就把这个引用干掉
        hashMap.remove(url);
        return null;
    }
}
