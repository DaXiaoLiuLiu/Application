package com.example.myapplication1.network;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.MyApplication;
import com.example.myapplication1.R;

public class VibratorHelper {

    public static void Vibrate(final Service service,long milliseconds){
        Vibrator vibrator = (Vibrator) service.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

   public static void Warning(Service service){
       NotificationManager manager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);

       NotificationChannel channel = null;
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           channel = new NotificationChannel("Warning","警告",NotificationManager.IMPORTANCE_HIGH);
           manager.createNotificationChannel(channel);
       }

       Intent intent = new Intent(service, MainActivity.class);
       PendingIntent pi = PendingIntent.getActivity(MyApplication.getContext(),0,intent,0);

       Notification notification = new NotificationCompat.Builder(MyApplication.getContext(),"DataService")
               .setContentTitle("智能安全监护系统")
               .setContentText("发现一处危险")
               .setSmallIcon(R.drawable.small_icon)
               .setLargeIcon(BitmapFactory.decodeResource(service.getResources(),R.drawable.large_icon))
               .setContentIntent(pi)
               .build();
       manager.notify(1,notification);
   }
}
