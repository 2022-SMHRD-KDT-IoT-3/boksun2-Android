package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class handiMedEtc extends AppCompatActivity {

    private Button btn_med_ok;
    private ImageButton btn_etc_sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_med_etc);

//        btn_etc_sound = findViewById(R.id.btn_med_sound);
//        btn_etc_sound.setColorFilter(Color.parseColor("#FFFF"));


        btn_med_ok = findViewById(R.id.btn_med_ok);
        btn_med_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);

                startActivity(intent);
                finish();
            }
        });
    }
}