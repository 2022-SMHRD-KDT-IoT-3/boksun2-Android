package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminBox extends AppCompatActivity {

    TextView tv_userName;

    // 보관함 버튼(1~7)
    Button box1, box2, box3, box4, box5, box6, box7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box);

        // 장애인 이름 넣기
        tv_userName = findViewById(R.id.tv_user_name);
        Intent intent = getIntent();
        String name =intent.getStringExtra("name");
        tv_userName.setText(name);

        //UserVO uvo = LoginCheck.uInfo;
        //tv_userName.setText(uvo.getUser_name() + "님의");

        // box1 버튼을 누르면 보관함 번호 box1 넘기기
        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box1
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box1");
                startActivity(intent);
            }
        });

        // box2 버튼을 누르면 보관함 번호 box2 넘기기
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box2
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box2");;
                startActivity(intent);
            }
        });

        // box3 버튼을 누르면 보관함 번호 box3 넘기기
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box3
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box3");
                startActivity(intent);
            }
        });

        // box4 버튼을 누르면 보관함 번호 box4 넘기기
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box4
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box4");
                startActivity(intent);
            }
        });

        // box5 버튼을 누르면 보관함 번호 box5 넘기기
        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box5
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box5");
                startActivity(intent);
            }
        });

        // box6 버튼을 누르면 보관함 번호 box6 넘기기
        box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box6
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box6");
                startActivity(intent);
            }
        });

        // box7 버튼을 누르면 보관함 번호 box7 넘기기
        box7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box7
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box7");
                startActivity(intent);
            }
        });

    }
}