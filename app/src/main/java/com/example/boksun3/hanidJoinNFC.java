package com.example.boksun3;

import static com.example.boksun3.handiLogin.nfc_serial_num;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class hanidJoinNFC extends AppCompatActivity {

    TextView tv_serialJoin;
    EditText edt_user_name, edt_user_emer;
    Button btn_handiNfcJoin;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanid_join_nfc);

        tv_serialJoin = findViewById(R.id.tv_serialJoin);
        edt_user_name = findViewById(R.id.edt_user_name);
        edt_user_emer = findViewById(R.id.edt_user_emer);
        btn_handiNfcJoin = findViewById(R.id.btn_handiNfcJoin);

        // 시리얼 번호 입력
        tv_serialJoin.setText(nfc_serial_num);
        //Log.v("nfc_serial_num", nfc_serial_num);
        edt_user_emer.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_handiNfcJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sR_serialJoin();
            }
        });
    }


    // 시리얼 번호 회원가입
    public void sR_serialJoin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/joinUserInsert.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("serialJoin", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "가입 성공", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user_id = jsonObject.optString("user_id");
                        String user_name = jsonObject.optString("user_name");
                        String user_joindate = jsonObject.optString("user_joindate");
                        String user_empn = jsonObject.optString("user_empn");
                        String user_access = jsonObject.optString("user_access");
                        int i=0;

                        // 로그인 체크 유지
                        LoginCheck.uInfo = new UserVO(user_id, user_name, user_joindate, user_empn, user_access, i);
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        
                        // 보관함 선택 페이지로 이동
                        Intent intent = new Intent(getApplicationContext(), handiBox.class);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "가입 실패", Toast.LENGTH_SHORT).show();
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

                String user_id = tv_serialJoin.getText().toString();
                String user_name = edt_user_name.getText().toString();
                String user_empn = edt_user_emer.getText().toString();

                params.put("user_id", user_id);
                params.put("user_name", user_name);
                params.put("user_empn", user_empn);

                return params;
            }
        };
        stringRequest.setTag("serialJoin");
        requestQueue.add(stringRequest);
    }
}