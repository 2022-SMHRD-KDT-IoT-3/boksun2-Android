package com.example.boksun3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;


public class handiLogin extends AppCompatActivity {

    ImageView img_speaker;
    EditText edt_userId;
    CheckBox checkBox_login;
    Button btn_handiLogin;

    // 시리얼 번호 저장
    public static String nfc_serial_num;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    // nfc
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_login);

        img_speaker = findViewById(R.id.img_speaker);
        img_speaker.setColorFilter(Color.parseColor("#8B8989"));

        edt_userId = findViewById(R.id.edt_userId);
        checkBox_login = findViewById(R.id.checkBox_login);
        btn_handiLogin = findViewById(R.id.btn_handiLogin);


        // 로그인 버튼(제품 시리얼 조회)
        btn_handiLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 시리얼 번호 조회
                sR_serialCheck();
            }
        });


        // nfc인스턴스 어뎁터 얻기
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            //NFC 미지원단말
            Toast.makeText(getApplicationContext(), "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        readFromTag(intent);
    }

    public void readFromTag(Intent intent){
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tag);
        try{
            ndef.connect();

            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (messages != null) {
                NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                for (int i = 0; i < messages.length; i++) {
                    ndefMessages[i] = (NdefMessage) messages[i];
                }
                NdefRecord record = ndefMessages[0].getRecords()[0];

                byte[] payload = record.getPayload();
                String text = new String(payload);
                text = text.substring(3); // 불필요한값 제외
                edt_userId.setText(text);

                // 시리얼 번호 저장
                nfc_serial_num = text;

                ndef.close();

            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Read From Tag.", Toast.LENGTH_LONG).show();
        }
    }

    // 시리얼 번호 조회
    public void sR_serialCheck() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/idCheckUser.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("serialCheck", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "회원 조회 성공", Toast.LENGTH_SHORT).show();
                    // 로그인 기능
                    sR_serialLogin();

                } else {
                    Toast.makeText(getApplicationContext(), "회원가입으로 이동", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), hanidJoinNFC.class);
                    startActivity(intent);

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

                String user_id = edt_userId.getText().toString();
                params.put("user_id", user_id);

                return params;
            }
        };
        stringRequest.setTag("serialCheck");
        requestQueue.add(stringRequest);
    }



    // 시리얼 번호 로그인
    public void sR_serialLogin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/loginUserSelect.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("serialLogin", response);

                if (response.length() > 0) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user_id = jsonObject.optString("user_id");
                        String user_name = jsonObject.optString("user_name");
                        String user_joindate = jsonObject.optString("user_joindate");
                        String user_empn = jsonObject.optString("user_empn");
                        String user_access = jsonObject.optString("user_access");
                        int i = 0;

                        // 로그인 체크 유지
                        LoginCheck.uInfo = new UserVO(user_id, user_name, user_joindate, user_empn, user_access, i);

                        Intent intent = new Intent(getApplicationContext(), handiBox.class);
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

                String user_id = edt_userId.getText().toString();

                params.put("user_id", user_id);

                return params;
            }
        };
        stringRequest.setTag("serialLogin");
        requestQueue.add(stringRequest);
    }




}
