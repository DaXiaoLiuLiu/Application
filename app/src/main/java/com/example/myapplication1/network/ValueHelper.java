package com.example.myapplication1.network;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValueHelper {
    public static final int UNSAFE = 1;
    public static final int SAFE = 0;
    public static final int TVALUE_1 = 2;//指示温度传感器,出现了危险状态
    public static final int TVALUE_2 = 3;//指示温度传感器
    public static final int SVALUE_1 = 4;//指示烟雾传感器，出现危险
    public static final int SVALUE_2 = 5;//指示烟雾传感器

    ImageView imageView;
    TextView textView, textView_t, textView_s;
    private  Activity valueActivity;

    public void setValueActivity(Activity activity){
        valueActivity = activity;
    }

    public Activity getValueActivity(){
        return valueActivity;
    }

    public void StatusCall(int status) {

        Boolean flags = false;
        //这里接收传感器数据和识别
        if (status == 0) flags = true;
        else flags = false;
        Message message = new Message();
        if (flags) {//flags == true即出现了不安全的情况
            message.what = UNSAFE;
            handler.sendMessage(message);
            //下面是利用通知发起警告
        } else {

            message.what = SAFE;
            handler.sendMessage(message);
        }
    }

    public void ValueCall(int tmp, int smoke) {
        int value_t, value_s;//s中0表示危险，1表示安全
        //这里是获取这些数据的逻辑
        value_s = smoke;
        value_t = tmp;

        Message message1 = new Message();
        if (value_t >= 35 || value_t < 5) {
            message1.what = TVALUE_1;//先更新温度数据
            message1.arg1 = value_t;
            handler.sendMessage(message1);
        } else {
            message1.what = TVALUE_1;//先更新温度数据
            message1.arg1 = value_t;
            handler.sendMessage(message1);
        }

        Message message2 = new Message();
        if (value_s == 0) {//危险状态
            message2.what = SVALUE_1;
            handler.sendMessage(message2);
        } else {
            message2.what = SVALUE_2;
            handler.sendMessage(message2);
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            imageView = getValueActivity().findViewById(R.id.imageView5);
            textView = getValueActivity().findViewById(R.id.textView);

            textView_t = getValueActivity().findViewById(R.id.textView_1);
            textView_s = getValueActivity().findViewById(R.id.textView_2);

            switch (msg.what) {
                case UNSAFE://这里进行不安全警告,使用通知等
                    imageView.setImageResource(R.drawable.danger);
                    textView.setText("不安全状态");
                    textView.setTextColor(Color.parseColor("#990033"));
                    break;
                case SAFE:
                    imageView.setImageResource(R.drawable.safe);
                    textView.setText("安全状态");
                    break;
                case TVALUE_1:
                    textView_t.setText(String.valueOf(msg.arg1) + "℃");
                    textView_t.setTextColor(Color.parseColor("#990033"));
                    break;
                case TVALUE_2:
                    textView_t.setText(String.valueOf(msg.arg1) + "℃");
                    textView_t.setTextColor(Color.parseColor("#33FF33"));
                case SVALUE_1:
                    //危险状态
                    textView_s.setText("异常");
                    textView_s.setTextColor(Color.parseColor("#990033"));
                    break;
                case SVALUE_2:
                    textView_s.setText("安全");
                    textView_s.setTextColor(Color.parseColor("#33FF33"));
                    break;
                default:
                    break;
            }
        }

    };


    public void UpdateUi() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://81.69.172.52:9999")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ValueService valueService = retrofit.create(ValueService.class);

                valueService.getValueData().enqueue(new Callback<Value>() {

                    int tmp, smoke, status;

                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        //对返回的数据进行处理
                        Value value = response.body();
                        tmp = value.getTmp();
                        smoke = value.getSmoke();
                        status = value.getStatus();

                        StatusCall(status);
                        ValueCall(tmp, smoke);

                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        Log.d("DataService", "Fail");
                        t.printStackTrace();
                    }
                });
            }
        }).start();
    }
}
