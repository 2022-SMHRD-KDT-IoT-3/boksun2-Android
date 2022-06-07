package com.example.boksun3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragementAdminUseradd extends Fragment {

    private EditText edt_serial, edt_handi_name, edt_handi_date, edt_handi_addr, edt_handi_phone, edt_handi_emer, edt_handi_disease;
    private RadioButton rbtn_male, rbtn_female;
    private Button btn_addr, btn_uAdd_cancel, btn_userAdd;

    // volley 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_useradd, container, false);

        // 주소
        edt_handi_addr = fragement.findViewById(R.id.edt_handi_addr);

        btn_addr = fragement.findViewById(R.id.btn_addr);
        btn_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), addressSearch.class);
                startActivity(intent);
            }
        });

        // 주소 intent로 가져오기
        Intent intent = new Intent(getContext(), addressSearch.class);
        String address = intent.getStringExtra("address");
        edt_handi_addr.setText(address);

        edt_serial = fragement.findViewById(R.id.edt_serial);
        edt_handi_name = fragement.findViewById(R.id.edt_handi_name);
        edt_handi_date = fragement.findViewById(R.id.edt_handi_date);
        edt_handi_addr = fragement.findViewById(R.id.edt_handi_addr);
        edt_handi_phone = fragement.findViewById(R.id.edt_handi_phone);
        edt_handi_emer = fragement.findViewById(R.id.edt_handi_emer);
        edt_handi_disease = fragement.findViewById(R.id.edt_handi_disease);
        rbtn_male = fragement.findViewById(R.id.rbtn_male);
        rbtn_female = fragement.findViewById(R.id.rbtn_female);
        btn_uAdd_cancel = fragement.findViewById(R.id.btn_uAdd_cancel);
        btn_userAdd = fragement.findViewById(R.id.btn_userAdd);


        // 장애인 등록
        btn_userAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sR_handiJoin();
            }
        });

        return fragement;
    }



    // 장애인 등록 기능
    public void sR_handiJoin() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/joinUserInsert.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("joinUser", response);

                if (response.length() > 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), handiLogin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
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

                String user_id = edt_serial.getText().toString();
                String user_name = edt_handi_name.getText().toString();
                String user_birthdate = edt_handi_date.getText().toString();
                String user_gender = "";
                if (rbtn_male.isChecked()) {
                    user_gender = "M";
                } else if (rbtn_female.isChecked()){
                    user_gender = "F";
                } else {
                    user_gender = "N";
                }
                String user_addr = edt_handi_addr.getText().toString();
                String user_phone = edt_handi_phone.getText().toString();
                String user_empn = edt_handi_emer.getText().toString();
                String user_disease = edt_handi_disease.getText().toString();

                // 복지사 아이디
                String worker_id = LoginCheck.wInfo.getWorker_id();

                // 날짜
                Date today = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String user_access = sdf.format(today).toString();

                params.put("user_id", user_id);
                params.put("user_name", user_name);
                params.put("user_birthdate", user_birthdate);
                params.put("user_gender", user_gender);
                params.put("user_addr", user_addr);
                params.put("user_phone", user_phone);
                params.put("user_empn", user_empn);
                params.put("user_disease", user_disease);
                params.put("user_access", user_access);
                params.put("worker_id", worker_id);

                return params;
            }
        };
        stringRequest.setTag("joinUser");
        requestQueue.add(stringRequest);
    }
}