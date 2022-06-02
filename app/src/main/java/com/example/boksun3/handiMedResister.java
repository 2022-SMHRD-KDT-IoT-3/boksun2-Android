package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class handiMedResister extends AppCompatActivity {

    private Button  btn_etc, btn_med_alarm;
    private ImageButton btn_med_sound;

    private EditText edt_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_med_resister);

        btn_med_sound = findViewById(R.id.btn_med_sound);
        btn_med_sound.setColorFilter(Color.parseColor("#000000"));


        btn_etc = findViewById(R.id.btn_etc);
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), handiMedEtc.class);
                startActivity(intent);

            }
        });

        btn_med_alarm = findViewById(R.id.btn_med_alarm);
        btn_med_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(getApplicationContext(), clickAddAlarm.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        int mHour = intent.getIntExtra("hh2",0);
        int mMinute = intent.getIntExtra("mm2", 0);




        edt_alarm = findViewById(R.id.edt_alarm);

        edt_alarm.setText(mHour + " : " + mMinute);


    }
}