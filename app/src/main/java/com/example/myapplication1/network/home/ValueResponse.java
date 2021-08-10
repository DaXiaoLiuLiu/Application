package com.example.myapplication1.network.home;

public class ValueResponse {
    private int temperatureStatus,smokeStatus,viewStatus;//0表示危险。1表示安全

    public ValueResponse(int temperatureStatus,int smokeStatus,int viewStatus){
        this.temperatureStatus = temperatureStatus;
        this.smokeStatus = smokeStatus;
        this.viewStatus = viewStatus;
    }
    public ValueResponse(){//空参构造器
    }

    public int getTemperatureStatus() {
        return temperatureStatus;
    }


    public int getSmokeStatus() {
        return smokeStatus;
    }


    public int getViewStatus() {
        return viewStatus;
    }


}
