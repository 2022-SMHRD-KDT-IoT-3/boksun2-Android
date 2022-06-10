package com.example.boksun3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class BokFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FMS";
    NotificationManager manager;
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "channel1";
    public BokFirebaseMessagingService() {

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, "onNewToken 호출됨 : "+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(TAG,"onMessageReceived() 호출됨.");

        String from = message.getFrom();
        Map<String, String> data = message.getData();
        String body = data.get("body");

        Log.d(TAG, "from" + from + ", body : " + body);
        sendToActivity(getApplicationContext(), from, body);
    }

    private void sendToActivity(Context context, String from, String body) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT));

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        }
        else{
            builder = new NotificationCompat.Builder(this);
        }

        builder.setContentTitle("긴급 알림");
        builder.setContentText(body);
        builder.setSmallIcon(R.drawable.icon);
        Notification noti = builder.build();

        manager.notify(1,noti);
    }
}