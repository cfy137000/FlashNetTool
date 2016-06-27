package com.lanou.chenfengyao.flashnet.image.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lanou.chenfengyao.flashnet.Option;
import com.lanou.chenfengyao.flashnet.utils.IOUtils;
import com.lanou.chenfengyao.flashnet.utils.MD5Util;
import com.lanou.chenfengyao.flashnet.utils.SDCardUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Risky on 15/10/20.
 */
public class DiskCache implements BitmapCache {

    private String cacheDir;
    //硬盘缓存的最小容量
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    //是否有硬盘缓存
    private boolean mIsDiskLruCacheCreated = false;
    private DiskLruCache mDiskLruCache;

    public DiskCache(Context mContext) {
        File diskCacheDir = SDCardUtils.getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        //如果可用空间大于设定的大小再初始化mDiskLruCache
        if (SDCardUtils.getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                //创建硬盘缓存成功
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        String key = MD5Util.getMD5String(url);
        DiskLruCache.Snapshot snapshot = null;
        FileInputStream fileInputStream = null;
        try {
            snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            }
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        } finally {
            IOUtils.close(fileInputStream);
        }


        return bitmap;
    }



    public void putBitmap(String url, Bitmap bitmap) {
        //向DiskCache里存
        if (mIsDiskLruCacheCreated) {
            String key = MD5Util.getMD5String(url);
            DiskLruCache.Editor editor = null;
            OutputStream outputStream = null;

            try {
                editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    outputStream = editor.newOutputStream(0);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    mDiskLruCache.flush();
                    editor.commit();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(outputStream);
            }

        }
    }
}
