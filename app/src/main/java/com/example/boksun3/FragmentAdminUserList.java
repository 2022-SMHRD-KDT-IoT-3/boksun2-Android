package com.example.boksun3;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class FragmentAdminUserList extends Fragment {
    private ListView listView;  // 검색을 보여줄 리스트변수
    private EditText editSearch; // 검색어를 입력할 Input 창

    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    private ArrayList<String> items = new ArrayList<>();  // 회원목록 list

    private ArrayList<String> arraylist;
    // 리스트의 모든 데이터를 arraylist에 복사한다.// items 복사본을 만든다.

    // 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    // 복지사 로그인 정보
    WorkerVO winfo = LoginCheck.wInfo;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_userlist, container, false);

        editSearch = fragement.findViewById(R.id.edt_userserach2);
        listView = fragement.findViewById(R.id.lv_userlist2);

/*        //등록된 회원리스트
        items = new ArrayList<String>(); //데이터를 넣은 리스트 변수
        items.add("송다민 " + "("+"광주 광산구 수완 양우내안애 아파트102-702"+")");
        items.add("2.김민근");
        items.add("3.김민정");
        items.add("4.신지수");
        items.add("5.윤솔아");

        arraylist = new ArrayList<String>();
        arraylist.addAll(items);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(items,getContext());

        // 리스트뷰에 아답터를 연결한다.
        listView.setAdapter(adapter);*/

        // 등록된 장애인 리스트
        // 페이지 이동이 되었을 때, 바로 목록이 보여져야 함
        sendRequestUserList();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);

            }
        });

        return fragement;
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        items.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            items.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    items.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }


    public void sendRequestUserList() {
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/userList.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("userList", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // jsonObject에는 회원들의 정보가 담겨 있다.
                        items.add(jsonObject.get("user_id")+" ("+jsonObject.get("user_addr")+")");

                    }

                    arraylist = new ArrayList<String>();
                    arraylist.addAll(items);

                    adapter = new SearchAdapter(items, getContext());
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    // json array 타입이 아닐 경우, 예외 처리
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
                //Log.v("worker_id", worker_id);

                params.put("worker_id", worker_id);

                return params;
            }
        };
        stringRequest.setTag("userList"); // 구분
        requestQueue.add(stringRequest);  // 전송
    }




}