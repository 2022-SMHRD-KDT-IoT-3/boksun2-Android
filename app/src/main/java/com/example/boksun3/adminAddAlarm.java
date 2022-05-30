package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class adminAddAlarm extends AppCompatActivity {

    Button btn_add;
    TextView tv_ampm, tv_time;
    ImageView alarm_set;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_alarm);

        alarm_set = findViewById(R.id.alarm_set);


        btn_add = findViewById(R.id.btn_add2);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), clickAddAlarm.class);
                startActivity(intent);

            }
        });


        Intent intent = getIntent();

        int mHour = intent.getIntExtra("hh",0);
        int mMinute = intent.getIntExtra("mm", 0);
        String mAMPM = intent.getStringExtra("a");


        tv_ampm = findViewById(R.id.tv_ampm2);
        tv_time = findViewById(R.id.tv_time2);

        tv_time.setText(mHour + " : " + mMinute);
        tv_ampm.setText(mAMPM);





    }

    public void onclick(View v){

        Intent intent = getIntent();

        int mHour = intent.getIntExtra("hh",0);
        int mMinute = intent.getIntExtra("mm", 0);
        String mAMPM = intent.getStringExtra("a");



        tv_ampm = findViewById(R.id.tv_ampm2);
        tv_time = findViewById(R.id.tv_time2);

        tv_time.setText(mHour + " : " + mMinute);
        tv_ampm.setText(mAMPM);


    }


}