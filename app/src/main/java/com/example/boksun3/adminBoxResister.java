package com.example.boksun3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class adminBoxResister extends AppCompatActivity {

    Button btn_add,btn_med_sub;
    TextView tv_time;
    EditText med_name, med_hosp,med_way,med_times,med_date, med_memo;


    // volley
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_box_resister);

        med_name = findViewById(R.id.med_name);
        med_hosp = findViewById(R.id.med_hosp);
        med_way = findViewById(R.id.med_way);
        med_times = findViewById(R.id.med_times);
        med_date = findViewById(R.id.med_date);
        med_memo = findViewById(R.id.med_memo);

        btn_med_sub = findViewById(R.id.btn_med_sub);



        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), clickAddAlarm.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();

        int mHour = intent.getIntExtra("hh",0);
        int mMinute = intent.getIntExtra("mm", 0);

        tv_time = findViewById(R.id.tv_time);
        tv_time.setText(mHour + " : " + mMinute);


        btn_med_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               sendRequestBoxResister();
            }
        });




    }

//
//    public void sendRequestBoxResister(){
//        // RequestQueue 객체 생성
//        requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//        // 서버에 요청할 주소
//
//
//        // 요청시 필요한 문자열 객체 (전송방식, url, 리스너)
//        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            // 응답데이터를 받아오는곳
//            @Override
//            public void onResponse(String response) {
//                Log.v("boxResister", response);
//
//                if (response.length() > 0) {
//                    Toast.makeText(getApplicationContext(), "보관함 등록 성공", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(getApplicationContext(), adminBox.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "보관함 등록 실패", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            // 서버와의 연동 에러시 출력
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }){
//            protected Response<String> parseNetworkResponse(NetworkResponse response){
//                try {
//                    String utf8String = new String(response.data, "UTF-8");
//                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
//                }catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }catch (Exception e){
//                    // log error
//                    return Response.error(new ParseError(e));
//
//                }
//            }
//
//            // 보낼 데이터를 저장하는 곳
//            protected Map<String,String> getParams() throws AuthFailureError{
//                Map<String,String> params = new HashMap<>();
//
//                String box_name = med_name.getText().toString();
//                String box_hosp = med_hosp.getText().toString();
//                String box_way = med_way.getText().toString();
//                String box_times = med_times.getText().toString();
//                String box_date = med_date.getText().toString();
//                String box_memo = med_memo.getText().toString();
//
//
//                params.put("box_name", box_name);
//                params.put("box_hosp", box_hosp);
//                params.put("box_way", box_way);
//                params.put("box_times", box_times);
//                params.put("box_date", box_date);
//                params.put("box_memo", box_memo);
//
//
//
//            }
//
//        }
//
//    }


}