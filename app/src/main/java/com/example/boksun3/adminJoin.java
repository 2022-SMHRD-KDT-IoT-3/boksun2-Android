package com.example.boksun3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import android.support.v4.app.*;

public class adminJoin extends AppCompatActivity {

    TextView edt_date , tv_address;
    EditText edt_address;
    Button btn_date, btn_address;

    // volley
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e ){
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_admin_join);


        getSupportActionBar().setTitle("회원가입");


//
//        edt_date = findViewById(R.id.edt_date);
//
//        edt_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getApplicationContext(), datePicker.class);
//
//                startActivity(intent);
//
//            }
//        });
//
//        btn_address = findViewById(R.id.btn_address);
//        btn_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), addressSearch.class);
//
//                startActivity(intent);
//            }
//        });
//
//
//        // 주소 intent로 가져오기
//        edt_address = findViewById(R.id.edt_address);
//        Intent intent = getIntent();
//        String address = intent.getStringExtra("address");
//        edt_address.setText(address);
//
//
//


        // 복지관명 intent로 가져오기


         Spinner s1 = findViewById(R.id.spinner1); // 도 선택 spinner
         Spinner s2 = findViewById(R.id.spinner2); // 대전 '구' spinner
         Spinner s3 = findViewById(R.id.spinner3); // 광주 '구' spinner

         tv_address = findViewById(R.id.tv_address);




         s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                 if(adapterView.getSelectedItem().equals("서울특별시")){
                     s2.setVisibility(View.VISIBLE);
                     s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> adapterView2, View view, int k, long l) {
                             tv_address.setText(adapterView.getItemAtPosition(i).toString()
                                      + " " + adapterView2.getItemAtPosition(k).toString());
                             s2.setVisibility(View.INVISIBLE);

                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> adapterView) {

                         }
                     });
                 }

                 else if(adapterView.getSelectedItem().equals("광주광역시")){
                     s3.setVisibility(View.VISIBLE);
                     s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> adapterView3, View view, int m, long l) {
                             tv_address.setText(adapterView.getItemAtPosition(i).toString()
                                     + " " + adapterView3.getItemAtPosition(m).toString());
                             s3.setVisibility(View.INVISIBLE);
                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> adapterView) {

                         }
                     });
                 }







             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });





    }
}