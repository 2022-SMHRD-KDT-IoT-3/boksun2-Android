package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminBoxResister extends AppCompatActivity {

    Button btn_add;
    TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box_resister);


        btn_add = findViewById(R.id.btn_add);
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




        tv_time = findViewById(R.id.tv_time);

        tv_time.setText(mHour + " : " + mMinute);




    }


}