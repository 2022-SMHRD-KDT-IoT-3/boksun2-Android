package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class adminJoin extends AppCompatActivity {

    EditText edt_date,edt_address;
    Button btn_date, btn_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_join);


        btn_date = findViewById(R.id.btn_date);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), datePicker.class);

                startActivity(intent);

            }
        });

        btn_address = findViewById(R.id.btn_address);
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), addressSearch.class);

                startActivity(intent);
            }
        });


        // 주소 intent로 가져오기
        edt_address = findViewById(R.id.edt_address);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        edt_address.setText(address);


        // 생년월일 intent로 가져오기
        edt_date =findViewById(R.id.edt_date);
        Intent intent2 = getIntent();
        int mYear = intent2.getIntExtra("mYear",0);
        int mMonth = intent2.getIntExtra("mMonth",0);
        int mDay = intent2.getIntExtra("mDay",0);

        edt_date.setText(mYear+ "년 " + mMonth + "월 " + mDay + "일");



    }
}