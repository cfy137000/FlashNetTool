package com.lanou.chenfengyao.flashnet.netengine.listenter;

import java.io.InputStream;

/**
 * Created by ChenFengYao on 16/6/20.
 * 网络请求成功的接口,可以扩展成各种返回值
 */
public interface SuccessListener {
    void onResponse(InputStream inputStream);
}
