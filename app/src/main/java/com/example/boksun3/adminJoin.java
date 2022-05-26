package com.example.boksun3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import android.support.v4.app.*;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class adminJoin extends AppCompatActivity {

    TextView tv_address;
    EditText edt_id, edt_pw, edt_name, edt_phone, edt_license, edt_com, edt_comTel;
    Button btn_idCheck, btn_submit;

    // volley
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.custom_bar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setContentView(R.layout.activity_admin_join);


        getSupportActionBar().setTitle("회원가입");

        edt_id = findViewById(R.id.edt_id);
        edt_pw = findViewById(R.id.edt_pw);
        edt_name = findViewById(R.id.edt_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_license = findViewById(R.id.edt_license);
        edt_com = findViewById(R.id.edt_com);
        edt_comTel = findViewById(R.id.edt_comTel);

        btn_idCheck = findViewById(R.id.btn_idCheck);
        btn_submit = findViewById(R.id.btn_submit);

        btn_idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestIdCheck();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestJoinWorker();
            }
        });


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


        // 복지관명 intent로 가져오기
        Spinner s1 = findViewById(R.id.spinner1); // 도 선택 spinner
        Spinner s2 = findViewById(R.id.spinner2); // 대전 '구' spinner
        Spinner s3 = findViewById(R.id.spinner3); // 광주 '구' spinner

        tv_address = findViewById(R.id.tv_address);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getSelectedItem().equals("서울특별시")) {
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
                    } else if (adapterView.getSelectedItem().equals("광주광역시")) {
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

    public void sendRequestIdCheck() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/idCheckWorker.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

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

                String worker_id = edt_id.getText().toString();

                params.put("worker_id", worker_id);

                return params;
            }
        };
        stringRequest.setTag("idCheck");
        requestQueue.add(stringRequest);
    }

    public void sendRequestJoinWorker() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/joinWorkerInsert.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("joinWorker", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
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

                String worker_id = edt_id.getText().toString();
                String worker_pw = edt_pw.getText().toString();
                String worker_name = edt_name.getText().toString();
                String worker_phone = edt_phone.getText().toString();
                String worker_license = edt_license.getText().toString();
                String worker_organization = edt_com.getText().toString();
                String worker_tel = edt_comTel.getText().toString();
                String worker_area = tv_address.getText().toString();

                params.put("worker_id", worker_id);
                params.put("worker_pw", worker_pw);
                params.put("worker_name", worker_name);
                params.put("worker_phone", worker_phone);
                params.put("worker_license", worker_license);
                params.put("worker_organization", worker_organization);
                params.put("worker_tel", worker_tel);
                params.put("worker_area", worker_area);

                return params;
            }
        };
        stringRequest.setTag("joinWorker");
        requestQueue.add(stringRequest);
    }
}