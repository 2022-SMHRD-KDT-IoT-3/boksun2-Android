package com.example.boksun3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "channel1";

    Button  btn_handiIn, btn_adminIn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_handiIn = findViewById(R.id.btn_handiIn);
        btn_adminIn = findViewById(R.id.btn_adminIn);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.w("Main", "토큰 가져오는 데 실패함", task.getException());
                    return;
                }
                String newToken = task.getResult();
                println("등록id : " + newToken);
                Log.v("newToken", newToken);
            }
        });

        // 장애인 버튼
        btn_handiIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiLogin.class);
                startActivity(intent);
            }
        });

        // 복지사 버튼
        btn_adminIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "복지사 페이지", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), adminLogin.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출됨");

        if(intent != null){
            processIntent(intent);
        }
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        String from = intent.getStringExtra("from");
        if(from == null){
            println("from is null");
            return;
        }

        String body = intent.getStringExtra("body");
        showNoti1(body);
        println("DATA : " + from + ", " + body);
    }
    public void println(String data){
        Log.v("text2", data);
    }

    public void showNoti1(String body){
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