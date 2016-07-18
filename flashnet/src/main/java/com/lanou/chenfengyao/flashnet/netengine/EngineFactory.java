package com.lanou.chenfengyao.flashnet.netengine;

import com.lanou.chenfengyao.flashnet.Option;
import com.lanou.chenfengyao.flashnet.netengine.connection.HttpTool;

/**
 * Created by ChenFengYao on 16/6/25.
 * 生产不同的网络引擎的工厂类
 *
 */
public class EngineFactory {
    public static NetEngine getDefaultEngine(){
      return getEngineWithMode(Option.getInstance().getEngineMode());
    }
    public static NetEngine getEngineWithMode(EngineMode engineMode){
        switch (engineMode){
            case HttpUrlConnection:
                return new HttpTool();
        }
        return null;
    }
}
