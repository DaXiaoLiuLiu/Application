package com.example.myapplication1.network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class DataService extends Service {

    Boolean ring  = false;//用于指示发出危险报警

    public DataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
         return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("DataService", "前台服务", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, "DataService")
                .setContentTitle("智能安全监护系统")
                .setContentText("正在运行中")
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.large_icon))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://81.69.172.52:9999")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ValueService valueService = retrofit.create(ValueService.class);

                while (true) {

                    try {
                        sleep(2000);//3秒钟发一个响应,以更新数据
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    valueService.getValueData().enqueue(new Callback<Value>() {
                        int tmp, smoke, status;

                        @Override
                        public void onResponse(Call<Value> call, Response<Value> response) {
                            //对返回的数据进行处理
                            Value value = response.body();
                            tmp = value.getTmp();
                            smoke = value.getSmoke();
                            status = value.getStatus();

                            //出现危险情况发出震动
                            if (status == 0 || smoke == 0 || tmp > 40 || tmp < 0) {
                                if(!ring) {
                                    VibratorHelper.Vibrate(DataService.this, 1000);
                                    //VibratorHelper.Warning(DataService.this);
                                    ring = true;
                                }
                            }
                            else{
                                ring = false;
                            }

                        }

                        @Override
                        public void onFailure(Call<Value> call, Throwable t) {
                            Log.d("DataService", "Fail");
                            Toast.makeText(MyApplication.getContext(),"网络连接失败",Toast.LENGTH_LONG).show();
                            t.printStackTrace();
                        }
                    });

                }//循环
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}


