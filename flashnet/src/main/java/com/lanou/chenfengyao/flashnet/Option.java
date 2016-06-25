package com.lanou.chenfengyao.flashnet;

import com.lanou.chenfengyao.flashnet.netengine.EngineMode;

/**
 * Created by ChenFengYao on 16/5/21.
 * 设置类
 */
public class Option {
    private int memoryMaxKB;
    private String dirPath;
    private EngineMode engineMode;

    public int getMemoryMaxKB() {
        return memoryMaxKB;
    }

    public void setMemoryMaxKB(int memoryMaxKB) {
        this.memoryMaxKB = memoryMaxKB;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    private static Option ourInstance;

    public static Option getInstance() {
        if (ourInstance == null) {
            synchronized (Option.class) {
                if (ourInstance == null) {
                    ourInstance = new Option();
                }
            }
        }

        return ourInstance;
    }

    //设置/获取 网络引擎
    public EngineMode getEngineMode() {
        return engineMode;
    }

    public void setEngineMode(EngineMode engineMode) {
        this.engineMode = engineMode;
    }

    private Option() {
        dirPath = "/sdcard/Download/";
        memoryMaxKB = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
        engineMode = EngineMode.HttpUrlConnection;
    }
}
