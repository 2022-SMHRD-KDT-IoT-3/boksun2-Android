package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class adminCall extends AppCompatActivity  {

    private ImageButton btn_sos;
    private Button btn_call;

    private ImageView img_home, img_hos, img_clock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_call);

        img_home = findViewById(R.id.img_home);
        img_hos = findViewById(R.id.img_hos);
        img_clock = findViewById(R.id.img_clock);

        img_home.setColorFilter(Color.parseColor("#DCDCDC"));
        img_clock.setColorFilter(Color.parseColor("#DCDCDC"));
        img_hos.setColorFilter(Color.parseColor("#DCDCDC"));




        //btn_call = findViewById(R.id.btn_call);
        btn_sos = findViewById(R.id.btn_sos);


        btn_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:010-0000-0000");
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
            }
        });



    }



}