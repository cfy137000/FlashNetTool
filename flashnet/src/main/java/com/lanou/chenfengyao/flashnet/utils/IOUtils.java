package com.lanou.chenfengyao.flashnet.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ChenFengYao on 16/5/21.
 */
public class IOUtils {
    public static void close(Closeable ... closeables){
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
