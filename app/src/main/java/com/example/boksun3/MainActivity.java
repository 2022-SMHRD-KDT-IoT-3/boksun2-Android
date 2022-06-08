package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button  btn_handiIn, btn_adminIn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_handiIn = findViewById(R.id.btn_handiIn);
        btn_adminIn = findViewById(R.id.btn_adminIn);


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