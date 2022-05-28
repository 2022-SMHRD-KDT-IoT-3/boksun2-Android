package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

public class handiLogin extends AppCompatActivity {

    ImageButton img_s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_login);

        img_s2 = findViewById(R.id.img_s2);
        img_s2.setColorFilter(Color.parseColor("#8B8989"));
    }
}