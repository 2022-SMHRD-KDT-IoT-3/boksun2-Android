package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class adminHandiJoin extends AppCompatActivity {

    private Button btn_address;
    EditText edt_handi_ad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handi_join);

        edt_handi_ad = findViewById(R.id.edt_handi_ad);

        // 주소찾기 버튼
        btn_address = findViewById(R.id.btn_address);
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminHandiJoin.this, addressSearch.class);

                startActivity(intent);
            }
        });


        // 주소 intent로 가져오기
        edt_handi_ad = findViewById(R.id.edt_handi_ad);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        edt_handi_ad.setText(address);

    }
}