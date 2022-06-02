package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class handiBox extends AppCompatActivity {

    TextView tv_handi_id;
    Button h_b_1, h_b_2, h_b_3, h_b_4, h_b_5, h_b_6, h_b_7;
    private int[] btn_ids = {R.id.h_b_1, R.id.h_b_2, R.id.h_b_3, R.id.h_b_4, R.id.h_b_5, R.id.h_b_6, R.id.h_b_7};
    private Button[] btns = new Button[7];

    /*private Button[] getBtns = {h_b_1, h_b_2, h_b_3, h_b_4, h_b_5, h_b_6, h_b_7};
    private String [] boxNums = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_box);

        // 아이디
        UserVO uvo = LoginCheck.uInfo;
        tv_handi_id = findViewById(R.id.tv_handi_id);
        tv_handi_id.setText(uvo.getUser_id());

        // 버튼 1~7
/*        for (int i = 0; i < btns.length; i++){
            btns[i] = findViewById(btn_ids[i]);
        }*/
        h_b_1 = findViewById(R.id.h_b_1);
        h_b_2 = findViewById(R.id.h_b_2);
        h_b_3 = findViewById(R.id.h_b_3);
        h_b_4 = findViewById(R.id.h_b_4);
        h_b_5 = findViewById(R.id.h_b_5);
        h_b_6 = findViewById(R.id.h_b_6);
        h_b_7 = findViewById(R.id.h_b_7);

        // 버튼 이벤트
/*        String [] boxNums = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};
        for (int i = 0; i < btns.length; i++){
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String boxNum = boxNums[];
                    Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                    intent.putExtra("boxNum", boxNum);
                    startActivity(intent);
                }
            });
        }*/

        h_b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box1";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box2";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box3";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box4";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box5";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box6";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });
        h_b_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box7";
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });


    }
}