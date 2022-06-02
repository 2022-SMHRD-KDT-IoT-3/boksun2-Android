package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class adminBox extends AppCompatActivity {

    TextView tv_box_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");

        tv_box_name = findViewById(R.id.tv_handi_name);

        tv_box_name.setText(name);

    }
}