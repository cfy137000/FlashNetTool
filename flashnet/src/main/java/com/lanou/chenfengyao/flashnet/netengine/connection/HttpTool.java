package com.lanou.chenfengyao.flashnet.netengine.connection;

import com.lanou.chenfengyao.flashnet.netengine.NetEngine;
import com.lanou.chenfengyao.flashnet.netengine.listenter.ErrorListener;
import com.lanou.chenfengyao.flashnet.netengine.listenter.SuccessListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ChenFengYao on 16/6/20.
 */
public class HttpTool implements NetEngine {
    @Override
    public void getData(String url, SuccessListener successListener, ErrorListener errorListener) {
        HttpURLConnection httpURLConnection = null;
        URL requestUrl = null;
        try {
            requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            if(inputStream!=null){

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
