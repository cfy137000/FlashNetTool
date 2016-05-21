package com.lanou.chenfengyao.flashnet.image.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lanou.chenfengyao.flashnet.Option;
import com.lanou.chenfengyao.flashnet.utils.IOUtils;
import com.lanou.chenfengyao.flashnet.utils.MD5Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Risky on 15/10/20.
 */
public class DiskCache implements BitmapCache{

    private  String cacheDir;

    public DiskCache(){
        cacheDir = Option.getInstance().getDirPath();
    }



    public Bitmap getBitmap(String url) {
        url = MD5Util.getMD5String(url);
        return BitmapFactory.decodeFile(cacheDir + url + ".png");
    }

    public void putBitmap(String url, Bitmap bitmap) {
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdir();
        }
        url = MD5Util.getMD5String(url);
        File imageFile = new File(cacheDir, url + ".png");
        if (!imageFile.exists()) {
            FileOutputStream fileOutputStream = null;
            try {
                imageFile.createNewFile();
                fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(fileOutputStream);
            }
        }
    }
}
