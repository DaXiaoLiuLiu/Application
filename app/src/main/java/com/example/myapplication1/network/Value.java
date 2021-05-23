package com.example.myapplication1.network;

public class Value {
    int tmp,smoke,status;//0表示危险。1表示安全

    public int getTmp() {
        return tmp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public int getSmoke() {
        return smoke;
    }

    public void setSmoke(int smoke) {
        this.smoke = smoke;
    }
}
