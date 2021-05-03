package com.example.myapplication1.ui.home;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import com.example.myapplication1.R;

public class ValueService extends Service {

    public static final int TVALUE = 0;//指示温度传感器
    public static final int SVALUE = 1;//指示烟雾传感器

    TextView textView_t,textView_s;//。第一个指示温度，第二个指示烟雾
    private ValueBinder mBinder1 = new ValueBinder();

    class ValueBinder extends Binder {//获取Fragment的实例
        private Activity myActivity1;
        public Activity getFragment1(Activity activity){
            myActivity1 = activity;
            return activity;//拿到Fragment对于的activity实例
        }

        public Activity getMyActivity1(){
            return myActivity1;
        }
    }

    public ValueService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder1;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {//这里是控制传感器的子进程
            @Override
            public void run() {
                do{
                    int value_t = 0,value_s = 1;//s中0表示危险，1表示安全

                    //这里是获取这些数据的逻辑
                    Message message = new Message();
                    message.what = TVALUE;//先更新温度数据
                    message.arg1 = value_t;
                    handler1.sendMessage(message);

                    message.what = SVALUE;
                    message.arg1 = value_s;
                    handler1.sendMessage(message);

                }while (true);
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler1 = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            textView_t = mBinder1.getMyActivity1().findViewById(R.id.textView_1);
            textView_s = mBinder1.getMyActivity1().findViewById(R.id.textView_2);

            switch (msg.what){
                case TVALUE:
                    textView_t.setText(String.valueOf(msg.arg1) + "?");
                    if(msg.arg1 >= 35 || msg.arg1 < 5){
                        textView_t.setTextColor(Color.parseColor("#990033"));
                    }
                    break;
                case SVALUE:
                    if(msg.arg1 == 0){//危险状态
                        textView_s.setText("烟雾浓度异常");
                        textView_s.setTextColor(Color.parseColor("#990033"));
                    }
                    else {
                        textView_s.setText("烟雾浓度正常");
                    }
                    break;
                default:
                    break;
            }
        }


    };
}
