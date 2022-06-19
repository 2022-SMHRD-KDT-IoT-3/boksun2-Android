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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class handiBox extends AppCompatActivity {

    TextView tv_handi_name;
    Button h_b_1, h_b_2, h_b_3, h_b_4, h_b_5, h_b_6, h_b_7;
    private int[] btn_ids = {R.id.h_b_1, R.id.h_b_2, R.id.h_b_3, R.id.h_b_4, R.id.h_b_5, R.id.h_b_6, R.id.h_b_7};
    private Button[] btns = new Button[7];
    private Button btn_handiLogout;

    /*private Button[] getBtns = {h_b_1, h_b_2, h_b_3, h_b_4, h_b_5, h_b_6, h_b_7};
    private String [] boxNums = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};*/

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;


    // 보관함 배열
    private String[] boxArray = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handi_box);

        // 이름
        UserVO uvo = LoginCheck.uInfo;
        tv_handi_name = findViewById(R.id.tv_handi_name);
        tv_handi_name.setText(uvo.getUser_name());
        Log.v("name", uvo.getUser_name());

        // 장애인 id 받기
        //LoginCheck.uInfo = new UserVO(user_id, user_name, user_joindate, user_empn, user_access, i);
        String user_id = LoginCheck.uInfo.getUser_id();
        LoginCheck.mNum = new MedicineVO(user_id,"0");


        // 버튼 1~7
/*        for (int i = 0; i < btns.length; i++){
            btns[i] = findViewById(btn_ids[i]);
        }*/
        h_b_1 = findViewById(R.id.h_b_1);
        h_b_2 = findViewById(R.id.h_b_2);
        h_b_3 = findViewById(R.id.h_b_3);
        h_b_4 = findViewById(R.id.h_b_4);
        h_b_5 = findViewById(R.id.h_b_5);
        h_b_6 = findViewById(R.id.h_b_6);
        h_b_7 = findViewById(R.id.h_b_7);

        // 로그아웃
        btn_handiLogout = findViewById(R.id.btn_handiLogout);

        // 보관함 조회 -> 사용 상태 체크
        sR_mediBoxSelect(boxArray);


        // 버튼 이벤트
/*        String [] boxNums = {"box1", "box2", "box3", "box4", "box5", "box6", "box7"};
        for (int i = 0; i < btns.length; i++){
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String boxNum = boxNums[];
                    Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                    intent.putExtra("boxNum", boxNum);
                    startActivity(intent);
                }
            });
        }*/

        h_b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box1");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box2");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box3");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box4");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box5");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), handiMedResister.class);
                intent.putExtra("med_box", "box6");
                intent.putExtra("user_id", user_id+"");
                startActivity(intent);
                finish();
            }
        });
        h_b_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String boxNum = "box7";
                Intent intent = new Intent(getApplicationContext(), Insert_nfc.class);
                intent.putExtra("boxNum", boxNum);
                startActivity(intent);
            }
        });


        // 로그아웃 버튼
        btn_handiLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginCheck.uInfo = null;
                SharedPreferencesManager_user.clearPreferences(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "로그아웃", Toast.LENGTH_SHORT).show();
                finishAffinity();

            }
        });
    }

    // 로그아웃 버튼 사용 하려고 뒤로가기 막음
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    // 보관함 사용 상태 체크
    public void sR_mediBoxSelect(String[] boxArray) {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/mediBoxSelect.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("boxState", response);

                try {
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
                        Button btnBox[] = {h_b_1, h_b_2, h_b_3, h_b_4, h_b_5, h_b_6, h_b_7};

                        if (med_box.equals(boxArray[i])) {   // "box1~7"
                            if (!med_name.equals(" ")) {     // 어떤 값이 들어있다면(약 이름 기준) 예) "고혈압약"
                                // 보관함 색 변경
                                // btnBox[i].setBackgroundColor(Color.parseColor("red"));
                                //보관함 버튼 변경
                                btnBox[i].setBackgroundResource(R.drawable.handi_border_y2);

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

                String user_id = LoginCheck.uInfo.getUser_id();
                params.put("user_id", user_id);
                Log.v("userId", user_id);
                return params;
            }
        };
        stringRequest.setTag("boxState");
        requestQueue.add(stringRequest);
    }

}