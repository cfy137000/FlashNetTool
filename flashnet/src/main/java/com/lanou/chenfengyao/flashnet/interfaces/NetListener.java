package com.lanou.chenfengyao.flashnet.interfaces;

/**
 * Created by ChenFengYao on 16/5/21.
 */
public interface NetListener<T> {
    void onNetSuccess(T t);
    void onError();
}
