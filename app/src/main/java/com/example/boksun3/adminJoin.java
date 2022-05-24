package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class adminJoin extends AppCompatActivity {

    EditText edt_date;
    Button btn_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_join);


        btn_date = findViewById(R.id.btn_date);





        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), datePicker.class);


                String mYear = intent.getStringExtra("mYear");
                int mMonth = intent.getIntExtra("mMonth", 0);
                int mDay = intent.getIntExtra("mDay",0);


                edt_date = findViewById(R.id.edt_date);
                edt_date.setText(mYear+"년 "+ mMonth + "월 " + mDay + "일 ");



                startActivity(intent);


            }
        });

    }
}