package com.lanou.chenfengyao.flashnet.netengine;

import android.graphics.Bitmap;

import com.lanou.chenfengyao.flashnet.netengine.listenter.ErrorListener;
import com.lanou.chenfengyao.flashnet.netengine.listenter.SuccessListener;

import java.io.InputStream;

/**
 * Created by ChenFengYao on 16/6/20.
 * 网络引擎的接口,定义了一些常用的方法
 */
public interface NetEngine {
    //获得get数据
    Response getData(String url);
    Bitmap getBitmap(String url,int reqW,int reqH);

}
