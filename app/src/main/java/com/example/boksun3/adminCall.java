package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class adminCall extends AppCompatActivity  {

    private ImageButton btn_sos;
    private Button btn_call;

    private ImageView img_home, img_hos, img_clock;

    private TextView tv_call_name,tv_call_date,tv_call_time,tv_call_phone,tv_call_addr,tv_callr_disease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_call);
        Log.v("calltest","긴급전화act");

        //선택된회원 정보
        String user_name = getIntent().getStringExtra("user_name");
        String user_addr = getIntent().getStringExtra("user_addr");
        String user_disease = getIntent().getStringExtra("user_disease");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");

        String user_phone = getIntent().getStringExtra("user_phone");

        tv_call_name = findViewById(R.id.tv_call_name);
        tv_call_addr = findViewById(R.id.tv_call_addr);
        tv_callr_disease = findViewById(R.id.tv_callr_disease);
        tv_call_date = findViewById(R.id.tv_call_date);
        tv_call_time = findViewById(R.id.tv_call_time);

        tv_call_name.setText(user_name);
        tv_call_addr.setText(user_addr);
        tv_callr_disease.setText(user_disease);
        tv_call_date.setText(date);
        tv_call_time.setText(time);


        img_home = findViewById(R.id.img_home);
        img_hos = findViewById(R.id.img_hos);
        img_clock = findViewById(R.id.img_clock);
        img_home.setColorFilter(Color.parseColor("#DCDCDC"));
        img_clock.setColorFilter(Color.parseColor("#DCDCDC"));
        img_hos.setColorFilter(Color.parseColor("#DCDCDC"));

        //btn_call = findViewById(R.id.btn_call);
        btn_sos = findViewById(R.id.btn_sos);

        // btn_sos 클릭시 dial로 이동하여 번호가 입력되도록
        btn_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri uri = Uri.parse("tel:010-0000-0000");
                Uri uri = Uri.parse("tel:"+user_phone);
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });



    }



}