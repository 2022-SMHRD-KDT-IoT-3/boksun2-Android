package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminInfo extends AppCompatActivity {

    private Button btn_sub;
    private TextView tv_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_info);


        tv_id = findViewById(R.id.tv_id_);
        btn_sub = findViewById(R.id.btn_info_ok);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FragmentAdminUserList.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        tv_id.setText(user_id);


    }
}