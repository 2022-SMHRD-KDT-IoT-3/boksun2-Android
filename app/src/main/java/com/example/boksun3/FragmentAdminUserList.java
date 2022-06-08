package com.example.boksun3;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentAdminUserList extends Fragment  {
    private ListView listView;  // 검색을 보여줄 리스트변수
    private EditText editSearch; // 검색어를 입력할 Input 창

    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    private ArrayList<String> items = new ArrayList<>();  // 회원목록 list

    private ArrayList<String> arraylist;
    private ArrayList<String> jarrylist = new ArrayList<>();
    // 리스트의 모든 데이터를 arraylist에 복사한다.// items 복사본을 만든다.

    // 민정
    private ArrayAdapter<String> adapter2;
    private Button btn_box_choice;
    private RadioButton rb_user_list;

    // 회원 상세
    private Button btn_user_detail;

    // 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    // 복지사 로그인 정보
    WorkerVO winfo = LoginCheck.wInfo;

    // 장애인 아이디 저장
    private ArrayList<String> user_ids = new ArrayList<String>();
    private String idChoice;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_userlist, container, false);
        // View view = inflater.inflate(R.layout.fg_admin_userlist, container,false);

        editSearch = fragement.findViewById(R.id.edt_userserach2);
        listView = fragement.findViewById(R.id.lv_userlist2);

        // 회원상세 버튼 클릭시
        btn_user_detail = fragement.findViewById(R.id.btn_user_detail);
        btn_user_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), adminHandiInfo.class);
                startActivity(intent);
            }
        });

        sendRequestUserList();

        btn_box_choice = fragement.findViewById(R.id.btn_box_choice);


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

        // 목록에서 장애인 선택 시, 보관함 선택 페이지로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 장애인 선택
                Toast.makeText(getContext(), items.get(i), Toast.LENGTH_SHORT).show();
                idChoice = user_ids.get(i);
                Log.v("idChoice", idChoice); // 아이디 확인

            }
        });

        // 보관함 선택으로 이동
        btn_box_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("idChoice", idChoice);
                Intent intent = new Intent(getActivity(),adminBox.class);
                intent.putExtra("user_id",idChoice);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setBackgroundColor(Color.RED);
                adapter2.notifyDataSetChanged();
                Toast.makeText(getContext(),"색깔변경",Toast.LENGTH_SHORT).show();

                return false;
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
        adapter2.notifyDataSetChanged();
    }


    public void sendRequestUserList() {
        jarrylist.clear();

        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/userList.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                //Log.v("userList", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jarrylist.add(jsonObject.get("user_name")+" ("+jsonObject.get("user_addr")+")");
                        // 장애인 아이디 저장
                        user_ids.add((String)jsonObject.get("user_id"));
                    }
                    items.clear();
                    for (int i = 0; i < jarrylist.size(); i++) {
                        items.add(jarrylist.get(i));
                    }

                    //라디오버튼
                    adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, items);
                    listView.setAdapter(adapter2);

                    //검색을 위해 복사
                    arraylist = new ArrayList<>();
                    arraylist.addAll(items);

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


    public void sendRequestUserChoice(String userChoice) {

        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://210.223.239.145:8081/controller/userChoice.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("userChoice", response);

                if (response.length() > 0) {
                    Toast.makeText(getContext(), "정보 선택 성공", Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String user_id = jsonObject.optString("user_id");
                        String user_name = jsonObject.optString("user_name");
                        String user_birthdate = jsonObject.optString("user_birthdate");
                        String user_gender = jsonObject.optString("user_gender");
                        String user_joindate = jsonObject.optString("user_joindate");
                        String user_addr = jsonObject.optString("user_addr");
                        String user_phone = jsonObject.optString("user_phone");
                        String worker_id = jsonObject.optString("worker_id");
                        String user_empn = jsonObject.optString("user_empn");
                        String user_access = jsonObject.optString("user_access");

                        //LoginCheck.uInfo = new UserVO(user_id, user_name, user_birthdate, user_gender, user_joindate, user_addr, user_phone, worker_id, user_empn, user_access);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "정보 선택 실패", Toast.LENGTH_SHORT).show();
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

                // 선택된 아이디 값 넘겨줌
                String user_id = userChoice;
                params.put("user_id", user_id);

                return params;
            }
        };
        stringRequest.setTag("userChoice"); // 구분
        requestQueue.add(stringRequest);  // 전송

    }

}