package com.example.boksun3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class handiJoin extends AppCompatActivity {

    EditText handi_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀바 문구 변경 코드
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_handi_join);
        getSupportActionBar().setTitle("회원가입");


        // 생년월일 intent로 가져오기
        handi_date = findViewById(R.id.handi_date);

        handi_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), datePicker.class);

                startActivity(intent);

            }
        });


        // 생년월일 intent로 가져오기
        handi_date =findViewById(R.id.handi_date);
        Intent intent2 = getIntent();
        int mYear = intent2.getIntExtra("mYear",0);
        int mMonth = intent2.getIntExtra("mMonth",0);
        int mDay = intent2.getIntExtra("mDay",0);

        handi_date.setText(mYear+ "년 " + mMonth + "월 " + mDay + "일");





    }
}