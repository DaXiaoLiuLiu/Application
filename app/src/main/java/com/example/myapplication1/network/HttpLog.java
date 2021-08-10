package com.example.myapplication1.network;

/*
 * @Author Lxf
 * @Date 2021/8/5 11:37
 * @Description 网络请求调试工具
 * @Since version-1.0
 */

import android.util.Log;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLog implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.d("HttpLogInfo", message);
    }
}
