package com.example.myapplication1;
/*
 * @Author Lxf
 * @Date 2021/8/5 11:36
 * @Description 网络部分和数据库部分的模块很多，这里进行获取数据的请求封装
 * 提供get和set方法，在后台服务中set变量值，提供get方法获取
 * @Since version-1.0
 */

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication1.network.home.ValueNetWork;
import com.example.myapplication1.network.home.ValueResponse;

public class Repository {
    private static ValueResponse vResult = new ValueResponse();
    private static MutableLiveData<ValueResponse> vDataResult
            = new MutableLiveData<>();
    
    
    public static Boolean setValue(){
        //简单的判空处理
        ValueResponse valueResponse = ValueNetWork.receiveValue();
        if(valueResponse != null){
            vResult = valueResponse;
            Log.d("Repository","tmp is " + vResult.getTemperatureStatus() );
            vDataResult.postValue(valueResponse);
            return true;
        }
        else {
            //测试用
            Log.d("Repository","tmp is " + vResult.getTemperatureStatus() );
            return false;
        }

    }
    //封装的获取valueResponse的方法
    public static MutableLiveData getValueData(){

        return vDataResult;
    }

    public static ValueResponse getValue()
    {
        return vResult;
    }
}
