package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adminBox extends AppCompatActivity {

    TextView tv_userName;

    // 보관함 버튼(1~7)
    Button btn_box1, btn_box2, btn_box3, btn_box4, btn_box5, btn_box6, btn_box7;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    // 보관함 배열
    private String[] boxArray = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box);

        // 장애인 이름 넣기
        tv_userName = findViewById(R.id.tv_user_name);
        UserVO uvo = LoginCheck.uInfo;

        // 장애인 id 받기
        String user_id =getIntent().getStringExtra("user_id");
        Log.v("box : user_id","" + user_id);

        tv_userName.setText(user_id + "님의");

        btn_box1 = findViewById(R.id.btn_box1);
        btn_box2 = findViewById(R.id.btn_box2);
        btn_box3 = findViewById(R.id.btn_box3);
        btn_box4 = findViewById(R.id.btn_box4);
        btn_box5 = findViewById(R.id.btn_box5);
        btn_box6 = findViewById(R.id.btn_box6);
        btn_box7= findViewById(R.id.btn_box7);



        // 보관함 조회 -> 사용 상태 체크
        sR_mediBoxSelect(boxArray);



        // box1 버튼을 누르면 보관함 번호 box1 넘기기
        btn_box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box1
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box1");
                startActivity(intent);
            }
        });

        // box2 버튼을 누르면 보관함 번호 box2 넘기기
        btn_box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box2
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box2");
                startActivity(intent);
            }
        });

        // box3 버튼을 누르면 보관함 번호 box3 넘기기
        btn_box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box3
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box3");
                startActivity(intent);
            }
        });

        // box4 버튼을 누르면 보관함 번호 box4 넘기기
        btn_box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box4
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box4");
                startActivity(intent);
            }
        });

        // box5 버튼을 누르면 보관함 번호 box5 넘기기
        btn_box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box5
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box5");
                startActivity(intent);
            }
        });

        // box6 버튼을 누르면 보관함 번호 box6 넘기기
        btn_box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box6
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box6");
                startActivity(intent);
            }
        });

        // box7 버튼을 누르면 보관함 번호 box7 넘기기
        btn_box7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 보관함 번호 넘겨주기 : box7
                Intent intent = new Intent(getApplicationContext(), adminBoxResister.class);
                intent.putExtra("med_box", "box7");
                startActivity(intent);
            }
        });



    }
    // 보관함 사용 상태 체크
    public void sR_mediBoxSelect(String[] boxArray) {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/mediBoxSelect.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("boxState", response);

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        int med_seq = Integer.parseInt(jsonObject.optString("med_seq"));
                        String user_id = jsonObject.optString("user_id");
                        String med_box = jsonObject.optString("med_box");
                        String med_name = jsonObject.optString("med_name");
                        String med_hosp = jsonObject.optString("med_hosp");
                        String med_way = jsonObject.optString("med_way");
                        String med_times = jsonObject.optString("med_times");
                        String med_date = jsonObject.optString("med_date");
                        String med_alarm = jsonObject.optString("med_alarm");
                        String med_update = jsonObject.optString("med_update");
                        String med_memo = jsonObject.optString("med_memo");

                        MedicineVO mvo = new MedicineVO(med_seq, user_id, med_box, med_name, med_hosp, med_way, med_times, med_date, med_alarm, med_update, med_memo);

                        // 보관함 버튼 배열
                        Button btnBox[] = {btn_box1, btn_box2, btn_box3, btn_box4, btn_box5, btn_box6, btn_box7};


                        if (med_box.equals(boxArray[i])) {   // "box1~7"
                            if (!med_name.equals("0")) {     // 어떤 값이 들어있다면(약 이름 기준) 예) "고혈압약"
                                // 보관함 색 변경
                                btnBox[i].setBackgroundColor(Color.parseColor("red"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

                String user_id =getIntent().getStringExtra("user_id");

                params.put("user_id", user_id);

                return params;
            }
        };
        stringRequest.setTag("boxState");
        requestQueue.add(stringRequest);

    }
}