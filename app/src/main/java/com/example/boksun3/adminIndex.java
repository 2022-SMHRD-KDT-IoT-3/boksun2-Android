package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminIndex extends AppCompatActivity {

    private Button btn_life, btn_userlist, btn_useradd, btn_set;
    private String menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        btn_life = findViewById(R.id.btn_life);
        btn_userlist =findViewById(R.id.btn_userlist);
        btn_useradd = findViewById(R.id.btn_useradd);
        btn_set = findViewById(R.id.btn_set);


        // 복지사 로그인 정보
        WorkerVO wInfo = LoginCheck.wInfo;


        //생활관리
        btn_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                menu = "life";
                intent.putExtra("menu", menu);
                startActivity(intent);
            }
        });

        btn_userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                menu = "userlist";
                intent.putExtra("menu", menu);
                startActivity(intent);
            }
        });
        btn_useradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                menu = "useradd";
                intent.putExtra("menu", menu);
                startActivity(intent);
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                menu = "set";
                intent.putExtra("menu", menu);
                startActivity(intent);
            }
        });




    }
}