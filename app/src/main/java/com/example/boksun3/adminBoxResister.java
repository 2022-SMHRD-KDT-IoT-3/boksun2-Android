package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class adminBoxResister extends AppCompatActivity {

    Button btn_add,btn_reset,btn_commit;
    TextView tv_boxnum,tv_time;
    EditText et_med_name,et_med_hosp,et_med_way, et_med_times,et_med_date,et_med_memo;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    String user_id;
    String med_box;

    //
    private ArrayList<String> medarraylist = new ArrayList<>();

    //보관함 배열
    private String[] medArray = {"med_name","med_hosp","med_way", "med_times", "med_date", "med_memo" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box_resister);

        user_id =getIntent().getStringExtra("user_id");
        med_box =getIntent().getStringExtra("med_box");

        LoginCheck.mNum = new MedicineVO(user_id, med_box);

        //MedicineVO mvo = LoginCheck.mNum;
        //mvo.getUser_id();
        //LoginCheck.mNum.get

        Log.v("box : user_idtest",LoginCheck.mNum.getUser_id());
        Log.v("box : med_box", med_box);
        Log.v("box : box_num", LoginCheck.mNum.getMed_box().substring(3));


        tv_boxnum = findViewById(R.id.tv_boxnum);
        tv_boxnum.setText(LoginCheck.mNum.getMed_box().substring(3)+"번");

        et_med_name = findViewById(R.id.et_med_name);
        et_med_hosp = findViewById(R.id.et_med_hosp);
        et_med_way = findViewById(R.id.et_med_way);
        et_med_times = findViewById(R.id.et_med_times);
        et_med_date = findViewById(R.id.et_med_date);
        et_med_memo = findViewById(R.id.et_med_memo);
        tv_time = findViewById(R.id.tv_time);

        btn_add = findViewById(R.id.btn_add);
        btn_reset = findViewById(R.id.btn_reset);
        btn_commit =findViewById(R.id.btn_commit);

        mediBoxInfoSelect(medArray);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), clickAddAlarm.class);
                intent.putExtra("admin","admin");
                startActivity(intent);
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestBoxResister();
            }
        });

        //clickAddAlarm
        Intent intent = getIntent();
        int mHour = intent.getIntExtra("hh",0);
        int mMinute = intent.getIntExtra("mm", 0);
        String check = intent.getStringExtra("ch");
        if( check != null ){
            tv_time.setText(mHour + " : " + mMinute);
        }

    }


    // 보관함 사용 상태 체크
    public void mediBoxInfoSelect( String[] medArray) {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/mediBoxInfoSelect.do";

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
                    String med_hosp = jsonObject.optString("med_hosp");
                    String med_way = jsonObject.optString("med_way");
                    String med_times = jsonObject.optString("med_times");
                    String med_date = jsonObject.optString("med_date");
                    String med_alarm = jsonObject.optString("med_alarm");
                    String med_update = jsonObject.optString("med_update");
                    String med_memo = jsonObject.optString("med_memo");

                    //MedicineVO mvo = new MedicineVO(med_seq, user_id, med_box, med_name, med_hosp, med_way, med_times, med_date, med_alarm, med_update, med_memo);

                    medarraylist.add(med_name);
                    medarraylist.add(med_hosp);
                    medarraylist.add(med_way);
                    medarraylist.add(med_times);
                    medarraylist.add(med_date);
                    medarraylist.add(med_memo);

                    Log.v("listtest",medarraylist.get(0)+"");
                    Log.v("medname",med_name);


                    EditText edt[] = {et_med_name,et_med_hosp,et_med_way, et_med_times,et_med_date,et_med_memo};

                    for(int i=0; i<medarraylist.size(); i++ ){
                        if(!medarraylist.get(i).equals(" ")){
                            edt[i].setText(medarraylist.get(i)+"");
                        }
                    }
                    tv_time.setText(med_alarm);

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

                //String user_id =getIntent().getStringExtra("user_id");

                params.put("user_id", user_id);
                params.put("med_box", med_box );

                return params;
            }
        };
        stringRequest.setTag("boxState");
        requestQueue.add(stringRequest);

    }


    // 약 보관함 업데이트
    public void sendRequestBoxResister() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/mediBoxUpdateW.do";

        // 요청시 필요한 문자열 객체 (전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는곳
            @Override
            public void onResponse(String response) {
                Log.v("boxResister", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "보관함 수정 성공", Toast.LENGTH_SHORT).show();

                    // 페이지 이동
                    Intent intent = new Intent(getApplicationContext(), adminMainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "보관함 수정 실패", Toast.LENGTH_SHORT).show();

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

                String box_name = et_med_name.getText().toString();
                String box_hosp = et_med_hosp.getText().toString();
                String box_way = et_med_way.getText().toString();
                String box_times = et_med_times.getText().toString();
                String box_date = et_med_date.getText().toString();
                String box_alarm = tv_time.getText().toString();
                String box_memo = et_med_memo.getText().toString();

                // 접속시간 -> 오늘 날짜 포매팅
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String box_update = sdf.format(today).toString();

                params.put("med_name", box_name);
                params.put("med_hosp", box_hosp);
                params.put("med_way", box_way);
                params.put("med_times", box_times);
                params.put("med_date", box_date);
                params.put("med_alarm", box_alarm);
                params.put("med_memo", box_memo);
                params.put("user_id", user_id);
                params.put("med_box", med_box);
                params.put("med_update", box_update);

                return params;
            }

        };
        stringRequest.setTag("boxResister");
        requestQueue.add(stringRequest);

    }

}