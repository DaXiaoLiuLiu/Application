package com.example.myapplication1.network;

/*
 * @Author Lxf
 * @Date 2021/8/5 9:37
 * @Description 服务创建器，由于含有泛型，因此创建方法不能为static
 * @Since version-1.0
 */

import com.example.myapplication1.BuildConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceCreator<T> {
    private static final String BASE_URL ="http://47.107.82.195:9999" ;

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(initHttpClient())
            .build();

    public  T ServiceCreate(Class<T> ServiceClass){
        T service = retrofit.create(ServiceClass);
        return service;
    }


    //打印网络请求日志
    private static OkHttpClient initHttpClient() {
        Interceptor logInterceptor;
        //处理网络请求的日志拦截输出
        if (BuildConfig.DEBUG) {
            //只显示基础信息
            logInterceptor = new HttpLoggingInterceptor(new HttpLog()).setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logInterceptor = new HttpLoggingInterceptor(new HttpLog()).setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return new OkHttpClient().newBuilder()
                .addInterceptor(logInterceptor)
                .build();

    }

}
