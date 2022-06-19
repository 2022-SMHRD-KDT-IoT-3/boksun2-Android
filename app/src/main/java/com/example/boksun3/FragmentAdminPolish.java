package com.example.boksun3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentAdminPolish extends Fragment {
    private ListView listView;

    private ArrayList<PolishVO> arraylist = new ArrayList<>();
    private PolishAdapter adapter;

    // 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    //복지사 로그인 정보
    WorkerVO winfo = LoginCheck.wInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_policy, container, false);

        listView = fragement.findViewById(R.id.lv_polishlist);

        sendRequestPolishList();

        return fragement;
    }

    //------------ 복지사 아이디로 회원목록을 불러오는 서버통신 메소드 ----------------------------
    public void sendRequestPolishList() {
        Log.v("sendRequestPolishList","sendRequestPolishList 실행");
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
                        String notice_type = jsonObject.optString ("notice_type");
                        String worker_id = jsonObject.getString("worker_id");
                        String notice_title= jsonObject.optString ("notice_title");
                        String notice_content = jsonObject.optString ("notice_content");
                        String notice_date = jsonObject.optString ("notice_date");
                        int notice_cnt = Integer.parseInt(jsonObject.optString ("notice_cnt"));

                        arraylist.add(new PolishVO(notice_type, worker_id, notice_title, notice_content, notice_date, notice_cnt));
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
}
