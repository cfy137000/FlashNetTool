package com.lanou.chenfengyao.flashnet.netengine;

import java.io.InputStream;

/**
 * Created by ChenFengYao on 16/6/25.
 */
public class Response {
    private InputStream inputStream;
    private int resultCode;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
