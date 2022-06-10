package com.example.boksun3;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class splashActivity extends AppCompatActivity {

    Animation anim_bok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try{
            Thread.sleep(1500);
            Intent intent = new Intent(splashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

}