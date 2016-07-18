package com.lanou.chenfengyao.nettooldemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lanou.chenfengyao.flashnet.image.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private String url = "http://img4.imgtn.bdimg.com/it/u=3990298113,517574477&fm=21&gp=0.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.mainIv);
        ImageLoader.getInstance(this).getImg(url,imageView);
    }
    
}
