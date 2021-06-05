package com.example.myapplication1.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ValueService {
    @GET("getStatus")//这里传入相对路径
    Call<Value> getValueData();
}
