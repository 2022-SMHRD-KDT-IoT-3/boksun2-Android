package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class handiMedResister extends AppCompatActivity {

    private ImageButton btn_med_sound;
    private Button btn_med_name, btn_med_alarm, btn_etc, btn_resister;
    private EditText edt_med_name, edt_alarm;
    private TextView tv_handi_boxnum;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    // 장애인 정보
    UserVO uvo = LoginCheck.uInfo;

    String user_id;
    String med_box;

    private ArrayList<String> medarraylist = new ArrayList<>();

    //보관함 배열
    private String[] medArray = {"med_name", "med_alarm"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_med_resister);

        user_id =getIntent().getStringExtra("user_id");
        med_box =getIntent().getStringExtra("med_box");

        LoginCheck.mNum = new MedicineVO(user_id, med_box);

        btn_med_name = findViewById(R.id.btn_med_name);
        btn_med_alarm = findViewById(R.id.btn_med_alarm);
        btn_etc = findViewById(R.id.btn_etc);
        btn_resister = findViewById(R.id.btn_resister);

        edt_med_name = findViewById(R.id.edt_med_name);
        edt_alarm = findViewById(R.id.edt_alarm);

        //보관함 번호 셋팅
        tv_handi_boxnum = findViewById(R.id.tv_handi_boxnum);
        tv_handi_boxnum.setText(LoginCheck.mNum.getMed_box().substring(3)+"번 보관함 등록");

        //보관함 정보 조회
        mediBoxInfoSelect(medArray);

        // 기타 페이지 이동
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), handiMedEtc.class);
                startActivity(intent);

            }
        });


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
                /*Intent intent2 = new Intent(getApplicationContext(), clickAddAlarm.class);
                startActivity(intent2);*/

            }
        });

/*
        Intent intent2 = getIntent();

        int mHour = intent2.getIntExtra("hh",0);
        int mMinute = intent2.getIntExtra("mm", 0);
*/


    }


    //뒤로 돌아갔을때 재실행 되도록
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), handiBox.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    };




    // 보관함 사용 상태 체크
    public void mediBoxInfoSelect( String[] medArray) {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/mediBoxInfoSelect.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("boxState", response);

                try{
                    JSONObject jsonObject = new JSONObject(response);

                    int med_seq = Integer.parseInt(jsonObject.optString("med_seq"));
                    String user_id = jsonObject.optString("user_id");
                    String med_box = jsonObject.optString("med_box");
                    String med_name = jsonObject.optString("med_name");
                    String med_alarm = jsonObject.optString("med_alarm");


                    //MedicineVO mvo = new MedicineVO(med_seq, user_id, med_box, med_name, med_hosp, med_way, med_times, med_date, med_alarm, med_update, med_memo);

                    medarraylist.add(med_name);
                    medarraylist.add(med_alarm);


                    Log.v("listtest",medarraylist.get(0)+"");
                    Log.v("medname",med_name);


                    EditText edt[] = {edt_med_name, edt_alarm};

                    for(int i=0; i<medarraylist.size(); i++ ){
                        if(!medarraylist.get(i).equals(" ")){
                            edt[i].setText(medarraylist.get(i)+"");
                        }
                    }
                    Log.v("tv_time",""+med_alarm);
                    if(med_alarm.equals(" ")){
                        edt_alarm.setText("00:00");
                    }else{
                        edt_alarm.setText(med_alarm);
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

                params.put("user_id", user_id);
                params.put("med_box", med_box );

                return params;
            }
        };
        stringRequest.setTag("boxState");
        requestQueue.add(stringRequest);

    }

    public void sR_insertMedicine() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/mediBoxUpdate.do";

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
                // UserVO uvo = LoginCheck.uInfo;
                // Intent intent = getIntent();
                // med_box = intent.getStringExtra("boxNum");

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