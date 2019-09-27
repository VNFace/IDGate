package com.enogy.detectedface.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.enogy.detectedface.uis.MainActivity;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.utils.Config;
import com.google.gson.JsonElement;

import retrofit2.Call;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class ServiceNotification extends Service {
    private Handler handler;
    private Intent intent;
    private String request = "hello";
    private NotificationManager manager;
    private NotificationCompat.Builder builder;
    private int count;
    private SharedPreferences sharedPreferences;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent();
        intent.putExtra(Config.KEY, request);
        intent.setAction(Config.ACTION);
        handler = new Handler();
        builder = new NotificationCompat.Builder(this, "channelId");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        count = 0;
        sharedPreferences = getSharedPreferences(Config.NAME, Context.MODE_PRIVATE);
        Log.e("TAG", "start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        requestServer();
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        return START_STICKY;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendBroadcast(intent);
            count++;
            if (count == 5){
                startForeground(1357, builder.build());
                count = 0;
            }
            requestServer();
        }
    };

    private void requestServer(){
        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.cancel(1357);
        handler.removeCallbacks(runnable);
    }

    private void createNotification(){
        builder.setSmallIcon(android.R.drawable.ic_menu_manage);
        builder.setContentTitle("New employee");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setAutoCancel(true);
        builder.setVisibility(VISIBILITY_PUBLIC);
    }
}
