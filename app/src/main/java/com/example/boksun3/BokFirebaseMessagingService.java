package com.example.boksun3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class BokFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FMS";

    public BokFirebaseMessagingService() {

    }

    @Override
    public void onNewToken(@NonNull String token) { // 새로운 토큰을 확인했을 때 호출되는 메서드
        super.onNewToken(token);
        Log.e(TAG, "onNewToken 호출됨 : "+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) { // 새로운 메시지를 받았을때 호출되는 메서드
        Log.d(TAG,"onMessageReceived() 호출됨.");

        String from = message.getFrom();
        Map<String, String> data = message.getData();
        String body = data.get("body");

        Log.d(TAG, "from" + from + ", body : " + body);
        sendToActivity(getApplicationContext(), from, body);
    }

    private void sendToActivity(Context context, String from, String body) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("body", body);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }
}