package com.example.myapplication1.network;

public class Value {
    private int temperatureStatus,smokeStatus,viewStatus;//0表示危险。1表示安全

    public int getTemperatureStatus() {
        return temperatureStatus;
    }

    public void setTemperatureStatus(int temperatureStatus) {
        this.temperatureStatus = temperatureStatus;
    }

    public int getSmokeStatus() {
        return smokeStatus;
    }

    public void setSmokeStatus(int smokeStatus) {
        this.smokeStatus = smokeStatus;
    }

    public int getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(int viewStatus) {
        this.viewStatus = viewStatus;
    }
}
