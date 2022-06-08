package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminHandiInfo extends AppCompatActivity {

    private Button btn_info_ok;
    private TextView tv_info_serial, tv_info_name, tv_info_date, tv_info_phone, tv_info_emer, tv_info_address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handi_info);

        tv_info_serial = findViewById(R.id.tv_info_serial);
        tv_info_name = findViewById(R.id.tv_info_name);
        tv_info_date = findViewById(R.id.tv_info_date);
        tv_info_phone = findViewById(R.id.tv_info_phone);
        tv_info_emer = findViewById(R.id.tv_info_emer);
        tv_info_address = findViewById(R.id.tv_info_address);




        btn_info_ok = findViewById(R.id.btn_info_ok);


        // 회원 상세정보 가져오기
        Intent intent2 = getIntent();

        String serial = intent2.getStringExtra("serial");
        String name = intent2.getStringExtra("name");
        String date = intent2.getStringExtra("date");
        String address = intent2.getStringExtra("address");
        String phone = intent2.getStringExtra("phone");
        String emer = intent2.getStringExtra("emer");


        tv_info_serial.setText(serial);
        tv_info_name.setText(name);
        tv_info_date.setText(date);
        tv_info_phone.setText(phone);
        tv_info_emer.setText(emer);
        tv_info_address.setText(address);


        btn_info_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),adminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }




}
