package com.lanou.chenfengyao.nettooldemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.lanou.chenfengyao.flashnet.image.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String url = "http://img4.imgtn.bdimg.com/it/u=3990298113,517574477&fm=21&gp=0.jpg";
    private List<String> urls;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_list);
        initData();
        MainAdapter mainAdapter = new MainAdapter(urls, this);
        listView.setAdapter(mainAdapter);
    }

    private void initData() {
        urls = new ArrayList<>();
        urls.add("http://img4.imgtn.bdimg.com/it/u=3990298113,517574477&fm=21&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=202573534,3270552252&fm=21&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=69152107,4147123589&fm=21&gp=0.jpg");
        urls.add("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&cs=172690696,3532821463&os=2940637495,3962710199&simid=4247603249,651065082&pn=2&rn=1&di=141380879970&ln=1000&fr=&fmq=1468899536746_R&fm=rs6&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=&istype=0&ist=&jit=&bdtype=0&gsm=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fpic.58pic.com%2F10%2F20%2F29%2F99bOOOPIC77.jpg&rpstart=0&rpnum=0");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2770031598,3824861243&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=3734611792,1769115946&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1920526865,307394821&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=553243176,565447188&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=3489056908,2288798584&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1778229674,1884213286&fm=23&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=2907112340,3926543487&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=128742603,3168023175&fm=23&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=3430005387,3724528790&fm=23&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=3145197354,3579780953&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2935114500,532022225&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1507932313,1621867955&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=3780467331,3692550031&fm=23&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=3931393380,57880633&fm=23&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=1222528126,2332838332&fm=23&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=3341049235,1329508122&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=2496654780,658444056&fm=23&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=1764815080,1504438157&fm=23&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=1155025668,1532874656&fm=23&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=1837216195,3839620626&fm=23&gp=0.jpg");
        urls.add("http://img2.imgtn.bdimg.com/it/u=3266432596,1188191228&fm=23&gp=0.jpg");
        urls.add("http://img1.imgtn.bdimg.com/it/u=1899085545,3948684692&fm=23&gp=0.jpg");
    }

}
