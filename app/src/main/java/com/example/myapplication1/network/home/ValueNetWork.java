package com.example.myapplication1.network.home;
/*
 * @Author Lxf
 * @Date 2021/8/5 10:45
 * @Description 收发主界面Value数据，并对数据进行简单的判空处理
 * @Since version-1.0
 */

import android.util.Log;

import com.example.myapplication1.network.ServiceCreator;

import java.io.IOException;

import retrofit2.Response;

public class  ValueNetWork {
    private static final ServiceCreator<ValueService> serviceCreator = new ServiceCreator<>();
    private static final ValueService valueService =
            serviceCreator.ServiceCreate(ValueService.class);
    private static Response<ValueResponse> response;
    private static ValueResponse NetWorkResult;

    //这里使用同步方法,返回值有可能为null
    public static final ValueResponse receiveValue(){
        try {
            response = valueService.getValueData().execute();
            //对返回体进行判空处理
            if(response.body() != null) {
                NetWorkResult = new ValueResponse();
                NetWorkResult = response.body();
            }
            else {
                NetWorkResult = new ValueResponse();
                Log.d("ValueNetWork","response is null");
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("valueNetWork","网络连接错误");
        }
        finally {

            return NetWorkResult;
        }
    }
}
