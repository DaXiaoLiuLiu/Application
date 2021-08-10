package com.example.myapplication1.network.home;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ValueService {
    @GET("getStatus")//这里传入相对路径
    Call<ValueResponse> getValueData();
}
