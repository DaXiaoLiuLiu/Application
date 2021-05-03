package com.example.myapplication1.ui.home;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;
import com.example.myapplication1.ui.home.HomeFragment;
import com.example.myapplication1.ui.notifications.NotificationsFragment;

import java.util.zip.Inflater;

import static java.lang.Thread.sleep;

public class DataService extends Service {

    public static final int UNSAFE = 1;
    public static final int SAFE = 0;
    public static final int TVALUE_1 = 2;//指示温度传感器,出现了危险状态
    public static final int TVALUE_2 = 3;//指示温度传感器
    public static final int SVALUE_1 = 4;//指示烟雾传感器，出现危险
    public static final int SVALUE_2 = 5;//指示烟雾传感器

    ImageView imageView;
    TextView textView,textView_t,textView_s;//第一个指示儿童检测。第二个指示温度，第三个指示烟雾
    private DataBinder mBinder = new DataBinder();

    class DataBinder extends Binder{//获取Fragment的实例
        private Activity myActivity;
        public Activity getFragment(Activity activity){
            myActivity = activity;
            return activity;//拿到Fragment对于的activity实例
        }

        public Activity getMyActivity(){
            return myActivity;
        }
    }

    public DataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {//这里是儿童检测的子进程
            @Override
            public void run() {
                Boolean flags = false;
                //这里接收传感器数据和识别

                Message message = new Message();
                if(flags == true){//出现了不安全的情况
                    message.what = UNSAFE;
                    handler.sendMessage(message);
                }
                else {

                    message.what = SAFE;
                    handler.sendMessage(message);
                }

                int value_t = 0,value_s = 1;//s中0表示危险，1表示安全

                //这里是获取这些数据的逻辑

                Message message1 = new Message();
                if(value_t >= 35 || value_t < 5){
                    message1.what = TVALUE_1;//先更新温度数据
                    message1.arg1 = value_t;
                    handler.sendMessage(message1);
                }
                else {
                    message1.what = TVALUE_1;//先更新温度数据
                    message1.arg1 = value_t;
                    handler.sendMessage(message1);
                }

                Message message2 = new Message();
                if(value_s == 0) {//危险状态
                    message2.what = SVALUE_1;
                    handler.sendMessage(message2);
                }
                else {
                    message2.what = SVALUE_2;
                    handler.sendMessage(message2);
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            imageView = mBinder.getMyActivity().findViewById(R.id.imageView5);
            textView = mBinder.getMyActivity().findViewById(R.id.textView);

            textView_t = mBinder.getMyActivity().findViewById(R.id.textView_1);
            textView_s = mBinder.getMyActivity().findViewById(R.id.textView_2);

            switch (msg.what){
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

}

