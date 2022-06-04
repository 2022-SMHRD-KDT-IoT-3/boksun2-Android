package com.example.boksun3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class adminLogin extends AppCompatActivity {

    EditText edt_id, edt_pw;
    Button btn_login ,tv_join;
    ImageView img1,img2;


    // volley
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        btn_login = findViewById(R.id.btn_login);
        tv_join = findViewById(R.id.tv_join);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);

        img1.setColorFilter(Color.parseColor("#DCDCDC"));
        img2.setColorFilter(Color.parseColor("#DCDCDC"));


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestLogin();
            }
        });

        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), adminJoin.class);
                startActivity(intent);
            }
        });


    }
    public void sendRequestLogin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/loginWorkerSelect.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("loginWorker", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String worker_id = jsonObject.getString("worker_id");
                        String worker_pw = jsonObject.getString("worker_pw");
                        String worker_name = jsonObject.getString("worker_name");
                        String worker_phone = jsonObject.getString("worker_phone");
                        String worker_license = jsonObject.getString("worker_license");
                        String worker_organization = jsonObject.getString("worker_organization");
                        String worker_tel = jsonObject.getString("worker_tel");
                        String worker_area = jsonObject.getString("worker_area");
                        String woker_joindate = jsonObject.getString("woker_joindate");

                        LoginCheck.wInfo = new WorkerVO(worker_id, worker_pw, worker_name, worker_phone, worker_license,
                                worker_organization, worker_tel, worker_area, woker_joindate);
                        Intent intent = new Intent(getApplicationContext(), adminIndex.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
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

                String worker_id = edt_id.getText().toString();
                String worker_pw = edt_pw.getText().toString();

                params.put("worker_id", worker_id);
                params.put("worker_pw", worker_pw);

                return params;
            }
        };
        stringRequest.setTag("loginWorker");
        requestQueue.add(stringRequest);
    }
}