
package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class adminHandiInfo extends AppCompatActivity {

    private Button btn_info_ok;
    private TextView tv_info_serial, tv_info_name, tv_info_date, tv_info_phone, tv_info_emer, tv_info_address;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handi_info);

        tv_info_serial = findViewById(R.id.tv_info_serial);
        tv_info_name = findViewById(R.id.tv_info_name);
        tv_info_date = findViewById(R.id.tv_info_date);
        tv_info_phone = findViewById(R.id.tv_info_phone);
        tv_info_emer = findViewById(R.id.tv_info_emer);
        tv_info_address = findViewById(R.id.tv_info_address);


        // 회원 정보 서버에 요청
        requestHandiInfo();

        // 확인 버튼 : 복지사 회원 목록 페이지로 이동
        btn_info_ok = findViewById(R.id.btn_info_ok);
        btn_info_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    // 회원 상세 보기
    public void requestHandiInfo(){
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/loginUserSelect.do";

        // 요청시 필요한 문자열 객체 (전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는곳
            @Override
            public void onResponse(String response) {
                Log.v("userInfo", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "회원 상세 정보", Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String user_id = jsonObject.optString("user_id");
                        String user_name = jsonObject.optString("user_name");
                        String user_phone = jsonObject.optString("user_phone");
                        String user_empn = jsonObject.optString("user_empn");
                        String user_birthdate = jsonObject.optString("user_birthdate");
                        user_birthdate = user_birthdate.substring(0, 10);
                        String user_addr = jsonObject.optString("user_addr");

                        LoginCheck.uInfo = new UserVO(user_id, user_name, user_birthdate, user_addr, user_phone, user_empn);
                        UserVO uvo = LoginCheck.uInfo;


                        tv_info_serial.setText(uvo.getUser_id());
                        tv_info_name.setText(uvo.getUser_name());
                        tv_info_phone.setText(uvo.getUser_phone());
                        tv_info_emer.setText(uvo.getUser_empn());
                        tv_info_date.setText(uvo.getUser_birthdate().substring(0,10));
                        tv_info_address.setText(uvo.getUser_addr());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "정보 불러오기 실패", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            // 서버와의 연동 에러시 출력
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
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
        stringRequest.setTag("userInfo");
        requestQueue.add(stringRequest);











    }




}

