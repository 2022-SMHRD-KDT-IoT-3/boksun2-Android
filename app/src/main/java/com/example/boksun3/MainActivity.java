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
    Button  btn_handiIn, btn_adminIn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Boksun3);
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
}