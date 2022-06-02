package com.example.boksun3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class handiJoin extends AppCompatActivity {

    ImageButton img_sound;
    EditText handi_id, handi_pw, handi_name, handi_date;
    RadioButton male, female;
    Button btn_handiIdCheck, btn_handiJoin;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀바 문구 변경 코드
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_handi_join);
        getSupportActionBar().setTitle("회원가입");

        img_sound = findViewById(R.id.btn_med_sound);

        img_sound.setColorFilter(Color.parseColor("#8B8989"));


        // 생년월일 intent로 가져오기
        handi_date = findViewById(R.id.handi_date);

        handi_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), datePicker.class);

                startActivity(intent);

            }
        });


        // 생년월일 intent로 가져오기

        Intent intent2 = getIntent();
        int mYear = intent2.getIntExtra("mYear", 0);
        int mMonth = intent2.getIntExtra("mMonth", 0);
        int mDay = intent2.getIntExtra("mDay", 0);

        handi_date.setText(mYear + " / " + mMonth + " / " + mDay);


        // 성별
        male = findViewById(R.id.rbtn_m);
        female = findViewById(R.id.rbtn_f);

        // 입력한 정보 가져오기
        handi_id = findViewById(R.id.handi_id);
        handi_pw = findViewById(R.id.handi_pw);
        handi_name = findViewById(R.id.handi_name);

        // 버튼
        btn_handiIdCheck = findViewById(R.id.btn_handiIdCheck);
        btn_handiJoin = findViewById(R.id.btn_handiJoin);

        // 장애인 아이디 중복 체크
        btn_handiIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sR_handiIdCheck();
            }
        });

        // 장애인 회원가입 버튼
        btn_handiJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sR_handiJoin();
            }
        });

    }

    public void sR_handiIdCheck() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/idCheckUser.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("idCheck", response);

                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        // json array 타입이 아닐 경우, 예외 처리
                        e.printStackTrace();
                    } finally {
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
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

                String user_id = handi_id.getText().toString();
                params.put("user_id", user_id);

                return params;
            }
        };
        stringRequest.setTag("idCheck");
        requestQueue.add(stringRequest);

    }


    public void sR_handiJoin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/joinUserInsert.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("joinUser", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), handiLogin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
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

                String user_id = handi_id.getText().toString();
                String user_pw = handi_pw.getText().toString();
                String user_name = handi_name.getText().toString();
                String user_date = handi_date.getText().toString();

                String user_gender;
                if (male.isChecked()) {
                    user_gender = "M";
                } else if (female.isChecked()){
                   user_gender = "F";
                } else {
                    user_gender = "N";
                }

                params.put("user_id", user_id);
                params.put("user_pw", user_pw);
                params.put("user_name", user_name);
                params.put("user_birthdate", user_date);
                params.put("user_gender", user_gender);

                return params;
            }
        };
        stringRequest.setTag("joinUser");
        requestQueue.add(stringRequest);
    }
}