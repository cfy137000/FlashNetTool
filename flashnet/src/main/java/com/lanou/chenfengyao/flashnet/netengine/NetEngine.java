package com.lanou.chenfengyao.flashnet.netengine;

import com.lanou.chenfengyao.flashnet.netengine.listenter.ErrorListener;
import com.lanou.chenfengyao.flashnet.netengine.listenter.SuccessListener;

/**
 * Created by ChenFengYao on 16/6/20.
 * 网络引擎的接口,定义了一些常用的方法
 */
public interface NetEngine {
    //获得get数据
    void getData(String url,
                 SuccessListener successListener,
                 ErrorListener errorListener);

}
