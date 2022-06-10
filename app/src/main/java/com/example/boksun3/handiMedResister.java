package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class handiMedResister extends AppCompatActivity {

    private ImageButton btn_med_sound;
    private Button btn_med_name, btn_med_alarm, btn_etc, btn_resister;
    private EditText edt_med_name, edt_alarm;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_med_resister);

//        btn_med_sound = findViewById(R.id.btn_med_sound);
//        btn_med_sound.setColorFilter(Color.parseColor("#000000"));
        btn_med_name = findViewById(R.id.btn_med_name);
        btn_med_alarm = findViewById(R.id.btn_med_alarm);
        btn_etc = findViewById(R.id.btn_etc);
        btn_resister = findViewById(R.id.btn_resister);
        edt_med_name = findViewById(R.id.edt_med_name);
        edt_alarm = findViewById(R.id.edt_alarm);

        // 기타 페이지 이동
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), handiMedEtc.class);
                startActivity(intent);

            }
        });

        // 보관함 번호 조회
        Intent intent = getIntent();
        String boxNum = intent.getStringExtra("boxNum");
        Log.v("boxNum", boxNum);

        // 약 정보 등록
        btn_resister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sR_insertMedicine();
            }
        });


        btn_med_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), clickAddAlarm.class);
                startActivity(intent2);

            }
        });

        Intent intent2 = getIntent();

        int mHour = intent.getIntExtra("hh",0);
        int mMinute = intent.getIntExtra("mm", 0);



        edt_alarm.setText(mHour + " : " + mMinute);



    }


    public void sR_insertMedicine() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/mediBoxUpdate.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("mediBoxInsert", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "약 등록 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), handiBox.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "약 등록 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override // response를 UTF8로 변경해주는 소스 코드(응답데이터 한글로 인코딩)
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }

            // 보낼 데이터를 저장하는 곳
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                // 회원 정보
                UserVO uvo = LoginCheck.uInfo;
                String user_id = uvo.getUser_id();
                Intent intent = getIntent();
                String med_box = intent.getStringExtra("boxNum");

                String med_name = edt_med_name.getText().toString();
                String med_alarm = edt_alarm.getText().toString();
                String med_hosp = "";
                String med_way = "";
                String med_times = "";
                String med_date = "";
                String med_memo = "";

                params.put("user_id", user_id);
                params.put("med_box", med_box);
                params.put("med_name", med_name);
                params.put("med_alarm", med_alarm);
                params.put("med_hosp", med_hosp);
                params.put("med_way", med_way);
                params.put("med_times", med_times);
                params.put("med_date", med_date);
                params.put("med_memo", med_memo);

                return params;
            }
        };
        stringRequest.setTag("mediBoxInsert");
        requestQueue.add(stringRequest);
    }








}