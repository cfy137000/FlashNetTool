package com.lanou.chenfengyao.flashnet.netengine.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lanou.chenfengyao.flashnet.netengine.NetEngine;
import com.lanou.chenfengyao.flashnet.netengine.Response;
import com.lanou.chenfengyao.flashnet.netengine.listenter.ErrorListener;
import com.lanou.chenfengyao.flashnet.netengine.listenter.SuccessListener;
import com.lanou.chenfengyao.flashnet.utils.BitmapHelper;
import com.lanou.chenfengyao.flashnet.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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
    public Response getData(String url) {
        HttpURLConnection httpURLConnection = null;
        URL requestUrl = null;
        InputStream inputStream = null;
        Response response = new Response();
        try {
            requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            inputStream = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            response.setResultCode(httpURLConnection.getResponseCode());
            response.setInputStream(bufferedInputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return response;
    }


    //使用HttpURLConnection 来加载图片的时候
    @Override
    public Bitmap getBitmap(String uri, int reqW, int reqH) {
        HttpURLConnection httpURLConnection = null;
        Bitmap bitmap = null;
        BufferedInputStream is = null;
        try {
            URL url = new URL(uri);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(httpURLConnection.getInputStream());
            is.mark(BitmapHelper.MAX_READ_LIMIT_PER_IMG);
            int scaleFactor = BitmapHelper.findScaleFactor(reqW, reqH, is);
            is.reset();
            bitmap = BitmapHelper.scaleBitmap(scaleFactor, is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
            httpURLConnection.disconnect();

        }

        return bitmap;
    }
}
