package com.lanou.chenfengyao.flashnet.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by ChenFengYao on 16/6/25.
 * 与屏幕相关的信息
 */
public class ScreenUtil {

    //获得屏幕宽高的方法
    public static Point getScreenParam(Context context){
        Point point = new Point();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(point);
        return point;
    }
}
