package com.example.myapplication1;

import android.app.Application;
import android.content.Context;
//获取全局context的量
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
