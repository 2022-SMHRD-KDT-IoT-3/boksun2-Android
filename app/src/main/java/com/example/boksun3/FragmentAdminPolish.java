package com.example.boksun3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentAdminPolish extends Fragment implements View.OnClickListener {
    private ListView listView;

    private ArrayList<PolishVO> arraylist = new ArrayList<>();
    private PolishAdapter adapter;

    // 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    private Button btn_regi, btn_send, btn_delete;
    private EditText edt_title, edt_content;
    //복지사 로그인 정보
    WorkerVO winfo = LoginCheck.wInfo;

    private int j = -1; // ItemClickListner에서 비교하기 위한 단순 변수
    private String requestSeq;
    private int requestcnt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_policy, container, false);

        listView = fragement.findViewById(R.id.lv_polishlist);

        btn_regi = fragement.findViewById(R.id.btn_regi);
        btn_send = fragement.findViewById(R.id.btn_send);
        btn_delete = fragement.findViewById(R.id.btn_delete);

        edt_title = fragement.findViewById(R.id.edt_title);
        edt_content = fragement.findViewById(R.id.edt_content);

//        chk_check = fragement.findViewById(R.id.chk_check);

        btn_regi.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        arraylist.clear();
        selectPolishList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 정책 선택
                if( i != j ){
                    view.setBackgroundColor(Color.parseColor("#ffab91"));
                    requestSeq = arraylist.get(i).getNotice_seq();
                    requestcnt = Integer.parseInt(arraylist.get(i).getNotice_cnt());
                    j = i;
                }
                else{
                    view.setBackgroundColor(Color.WHITE);
                    requestSeq = null;
                    requestcnt = 0;
                    j = -1;
                }
            }
        });
        return fragement;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regi:
                registerPolishList();
                break;
            case R.id.btn_delete:
                deletePolishList();
                break;
            case R.id.btn_send:
                sendPolishList();
                break;
        }
    }
    //------------ 복지사 아이디로 회원목록을 불러오는 서버통신 메소드 ----------------------------
    public void selectPolishList() {
        Log.v("selectPolishList","selectPolishList 실행");
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://172.30.1.29:8081/controller/NoticeList.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("NoticeList", response);
                try {
                    if(response.isEmpty()){
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // jsonObject에는 회원들의 정보가 담겨 있다.
                        // jsonObject = inex = 1인 회원정보가 담긴 배열
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String notice_seq = jsonObject.optString("notice_seq");
                        String notice_type = jsonObject.optString ("notice_type");
                        String worker_id = jsonObject.getString("worker_id");
                        String notice_title= jsonObject.optString ("notice_title");
                        String notice_content = jsonObject.optString ("notice_content");
                        String notice_date = jsonObject.optString ("notice_date").substring(0,10);
                        String notice_cnt = jsonObject.optString ("notice_cnt");
                        String notice_chk = jsonObject.optString("notice_chk");

                        arraylist.add(new PolishVO(notice_seq, notice_type, worker_id, notice_title, notice_content, notice_date, notice_cnt, notice_chk));
                    }
                    adapter = new PolishAdapter(arraylist);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
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

                String worker_id = winfo.getWorker_id();
                Log.v("worker_id", worker_id);

                params.put("worker_id", worker_id);

                return params;
            }
        };
        stringRequest.setTag("polishList"); // 구분
        requestQueue.add(stringRequest);  // 전송
    }
    public void registerPolishList() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://172.30.1.29:8081/controller/regiNoticeList.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("regiNoticeList", response);

                if (response.length() > 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "등록 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), adminMainActivity.class);
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

                String notice_title = edt_title.getText().toString();
                String notice_content = edt_content.getText().toString();
                int notice_cnt = 0;
                String worker_id = winfo.getWorker_id();
                String notice_chk = "0";

                params.put("notice_title", notice_title);
                params.put("notice_content", notice_content);
                params.put("notice_cnt", String.valueOf(notice_cnt));
                params.put("worker_id", worker_id);
                params.put("notice_chk", notice_chk);

                return params;
            }
        };
        stringRequest.setTag("regiNoticeList");
        requestQueue.add(stringRequest);
    }

    public void deletePolishList() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://172.30.1.29:8081/controller/deletePolishList.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("deletePolishList", response);

                if (response.length() > 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "삭제 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), adminMainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
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

                params.put("notice_seq", requestSeq);
                params.put("worker_id", winfo.getWorker_id());

                return params;
            }
        };
        stringRequest.setTag("deletePolishList");
        requestQueue.add(stringRequest);
    }

    public void sendPolishList() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // 서버에 요청할 주소
        String url = "http://172.30.1.29:8081/controller/sendPolishList.do"; // 휴대폰에서 요청가능하도록 내 IPv4 입력

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("sendPolishList", response);

                if (response.length() > 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "전송 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), adminMainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT).show();
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

                params.put("notice_seq", requestSeq);
                params.put("worker_id", winfo.getWorker_id());
                params.put("notice_chk", "Y");
                params.put("notice_cnt", String.valueOf(requestcnt));

                return params;
            }
        };
        stringRequest.setTag("sendPolishList");
        requestQueue.add(stringRequest);
    }
}
