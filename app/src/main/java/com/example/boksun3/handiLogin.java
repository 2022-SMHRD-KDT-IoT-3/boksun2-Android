package com.example.boksun3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Locale;
import java.util.Map;

public class handiLogin extends AppCompatActivity {

    ImageButton img_speaker;
    TextView tv_guide;
    EditText edt_userId, edt_userPw;
    Button btn_next;

    // TTS 변수
    private TextToSpeech tts;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_login);

        img_speaker = findViewById(R.id.img_speaker);
        img_speaker.setColorFilter(Color.parseColor("#8B8989"));
        tv_guide = findViewById(R.id.tv_guide);
        edt_userId = findViewById(R.id.edt_userId);
        edt_userPw = findViewById(R.id.edt_userPw);
        btn_next = findViewById(R.id.btn_next);


        // TTS를 생성하고 OnInitListener로 초기화
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != android.speech.tts.TextToSpeech.ERROR) {
                    // 언어를 선택
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        // 안내음성 출력 버튼
        img_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("=== 음성 출력 ===");
                String guide = "안녕";

                // 작성한 문장을 읽어줌
                tts.setPitch(1.5f);  // 1.5톤 올려서
                tts.speak(guide,TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        // 다음 버튼 : 로그인
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 시리얼 번호 조회
                sR_serialCheck();
            }
        });
    }

    // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
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
                    Toast.makeText(getApplicationContext(), "회원 가입 진행", Toast.LENGTH_SHORT).show();
                    // 회원가입 기능
                    sR_serialJoin();
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
                        String user_id = jsonObject.getString("user_id");
                        String user_pw = jsonObject.getString("user_pw");
                        String user_joindate = jsonObject.getString("user_joindate");

                        LoginCheck.uInfo = new UserVO(user_id, user_pw, user_joindate);

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
                String user_pw = edt_userPw.getText().toString();

                params.put("user_id", user_id);
                params.put("user_pw", user_pw);

                return params;
            }
        };
        stringRequest.setTag("serialLogin");
        requestQueue.add(stringRequest);
    }


    // 시리얼 번호 회원가입
    public void sR_serialJoin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/serialJoin.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("serialJoin", response);

                if (response.length() > 0) {
                    //Toast.makeText(getApplicationContext(), "가입 성공", Toast.LENGTH_SHORT).show();
                    sR_serialLogin();

                } else {
                    //Toast.makeText(getApplicationContext(), "가입 실패", Toast.LENGTH_SHORT).show();
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
                String user_pw = edt_userPw.getText().toString();

                params.put("user_id", user_id);
                params.put("user_pw", user_pw);

                return params;
            }
        };
        stringRequest.setTag("serialJoin");
        requestQueue.add(stringRequest);
    }
}