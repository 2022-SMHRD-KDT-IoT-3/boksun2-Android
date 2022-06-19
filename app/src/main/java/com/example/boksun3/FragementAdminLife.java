package com.example.boksun3;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FragementAdminLife extends Fragment {
    private ListView listView;
    private EditText editSearch; // 검색어를 입력할 Input 창

    private ArrayList<UserVO> arraylist = new ArrayList<>();  //실제 데이터 (리스트뷰에)들어가는 곳
    private ArrayList<UserVO> items; //복사할 곳
    private ArrayList<UserVO> userarraylist = new ArrayList<>(); //서버와 통신한 회원 전체 정보 날짜 아이디값 받아오는곳

    private SearchLifeAdapter adapter;

    // 서버 통신
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    //복지사 로그인 정보
    WorkerVO winfo = LoginCheck.wInfo;

    private String condicheck;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_life, container,false);

        editSearch = fragement.findViewById(R.id.edt_lifeSearch);
        listView = fragement.findViewById(R.id.lv_lifelist);

        //서버통신
        sendRequestUserList();


        //listview각 아이템 클릭시 ->긴급전화로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(fragement.getContext(), position+ "번째 목록", Toast.LENGTH_SHORT).show();
                Log.v("username",".."+arraylist.get(position).getUser_name());
                Log.v("phone","phone"+userarraylist.get(position).getUser_phone());

                Intent intent = new Intent(getActivity(),adminCall.class);
                intent.putExtra("user_name" ,arraylist.get(position).getUser_name());
                intent.putExtra("user_addr",userarraylist.get(position).getUser_addr());
                intent.putExtra("user_disease",userarraylist.get(position).getUser_disease());
                intent.putExtra("date" ,arraylist.get(position).getDate());
                intent.putExtra("time" ,arraylist.get(position).getTime());

                intent.putExtra("user_phone",userarraylist.get(position).getUser_phone());

                startActivity(intent);
            }
        });

        //검색
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //input창에 문자를 입력할때마다 호출
                //serach메소드 호출
                String text = editSearch.getText().toString();
                search(text);
            }
        });
        Log.v("fg", "life프래그먼트실행");

        return fragement;
    }

    //------------ 복지사 아이디로 회원목록을 불러오는 서버통신 메소드 ----------------------------
    public void sendRequestUserList() {
        userarraylist.clear();
        Log.v("sendRequestUserList","sendRequestUserList 실행");
        // RequestQueue 객체 생성
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // 서버에 요청할 주소
        String url = "http://220.80.88.88:8081/controller/userList.do";

        // 요청 시 필요한 문자열 객체(전송방식, url, 리스너)
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            // 응답데이터를 받아오는 곳
            @Override
            public void onResponse(String response) {
                Log.v("userLifeList", response);
                //userarraylist = new ArrayList<UserVO>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // jsonObject에는 회원들의 정보가 담겨 있다.
                        // jsonObject = inex = 1인 회원정보가 담긴 배열
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user_id = jsonObject.optString ("user_id");
                        String user_name= jsonObject.optString ("user_name");
                        String user_addr = jsonObject.optString ("user_addr");
                        String user_phone = jsonObject.optString ("user_phone");
                        String worker_id = jsonObject.getString("worker_id");
                        String user_access = jsonObject.optString ("user_access");
                        String user_disease = jsonObject.optString ("user_disease");

                        //Log.v("usertest", user_access);

                        UserVO vo = new UserVO(user_id,user_name,user_addr,user_phone,worker_id,user_access,user_disease);
                        userarraylist.add(vo);

                        //Log.v("`testlife`", userarraylist.get(i)+"");
                        //  Log.v("test111", userarraylist.get(5).getUser_access()+"");
                    }

                    //환자목록셋팅
                    //arraylist.add(new UserVO("송다민","광주광산구양우내안애아파트","2022.06.02","14:00","안전"));
                    arraylist.clear();
                    for (int i = 0; i < userarraylist.size(); i++) {
                        String ac = userarraylist.get(i).getUser_access();
                        String user_name = userarraylist.get(i).getUser_name();
                        String user_addr = userarraylist.get(i).getUser_addr();
                        String date = ac.substring(0,10);
                        String time = ac.substring(11);;
                        String condition = datecheck(ac);
                        arraylist.add(new UserVO(user_name,user_addr,date,time,condition));
                    }

                    Log.v("리스트뷰list",arraylist.get(0)+"");
                    adapter = new SearchLifeAdapter(arraylist);
                    listView.setAdapter(adapter);

                    //검색을 위해서 복사
                    items = new ArrayList<UserVO>();
                    items.addAll(arraylist);
                    //conditioncheck();

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
        stringRequest.setTag("userList"); // 구분
        requestQueue.add(stringRequest);  // 전송

    }

    // -------------- 검색기능  --------------------------------------------
    public void search(String charText){

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        arraylist.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            arraylist.addAll(items);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < items.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                // 이름이 일치할 경우
                if (items.get(i).getUser_name().toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    arraylist.add(items.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    //----------- 날짜 계산 위험도 체크 ---------------------------------------------------------------------
    public String datecheck(String ac){
        try {

            //데이터포맷
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String nowTime = sdf1.format(System.currentTimeMillis() + 32400000);
            String lastTime = sdf1.format(sdf1.parse(ac));

            Date time1 = sdf1.parse(nowTime);
            Date time2 = sdf1.parse(lastTime);

            //현재시간 - 마지막 접속시간
            long duration = time1.getTime() - time2.getTime();
            long minus = duration/1000/3600/24;
            int minusday = Long.valueOf(minus).intValue();

            if(minusday<7){
                condicheck = "안전";
            }else if(minus>=7){
                condicheck = "위험";
            }

//            Log.v("테스트",nowTime );
//            Log.v("테스트", lastTime);
//            Log.v("테스트",minusday+"" );
//            Log.v("테스트",condicheck+"" );
//            Log.v("일수차이", String.valueOf(duration/1000/3600/24));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return condicheck;
    }

    //------------- 시간차이별로 위험과 안전을 판단해서 배열생성하는 메소드 -----------------
//    public void conditioncheck() {
//        Log.v("conditioncheck","conditioncheck");
//
//        //2022-05-15 08:19
//        //arraylist.add(new UserVO("songdamin","광주광산구양우내안애아파트","2022.06.02","14:00","안전"));
//        //user_id,user_name,user_addr,user_phone,worker_id,user_access
//       //arraylist.add(new UserVO("송다민","광주광산구양우내안애아파트","2022.06.02","14:00","안전"));
//
//        for (int i = 0; i < userarraylist.size(); i++) {
//            String ac = userarraylist.get(i).getUser_access();
//            //Log.v("gggg",ac);
//            String user_name = userarraylist.get(i).getUser_name();
//            String user_addr = userarraylist.get(i).getUser_addr();
//            String date = ac.substring(0,9);
//            String time = ac.substring(11);;
//            String condition = datecheck(ac);
//            arraylist.add(new UserVO(user_name,user_addr,date,time,condition));
//        }
//        Log.v("condi",arraylist.get(0)+"");
//    }


}
