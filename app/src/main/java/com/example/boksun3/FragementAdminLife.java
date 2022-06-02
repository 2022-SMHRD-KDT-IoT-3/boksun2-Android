package com.example.boksun3;

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
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragementAdminLife extends Fragment {
    private ListView listView;
    private EditText editSearch; // 검색어를 입력할 Input 창

    private ArrayList<UserVO> arraylist;  //실제 데이터 들어가는 곳
    private ArrayList<UserVO> items; //복사할 곳

    private SearchLifeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_life, container,false);

        editSearch = fragement.findViewById(R.id.edt_lifeSearch);
        listView = fragement.findViewById(R.id.lv_lifelist);

        //등록된 환자
        arraylist = new ArrayList<>();

        arraylist.add(new UserVO("songdamin","광주광산구양우내안애아파트","2022.06.02","14:00","안전"));
        arraylist.add(new UserVO("송다민","광주광산구양우내안애아파트","2022.06.02","14:00","위험"));
        arraylist.add(new UserVO("송다민","광주광산구양우내안애아파트","2022.06.02","14:00","안전"));

        //검색을 위해서 복사
        items = new ArrayList<UserVO>();
        items.addAll(arraylist);

        //어댑터생성 및 셋팅
        adapter = new SearchLifeAdapter(arraylist);
        listView.setAdapter(adapter);

        //listview각 아이템 클릭시
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(fragement.getContext(), position+ "번째 목록", Toast.LENGTH_SHORT).show();
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
        Log.v("myData", "프래그먼트실행");

        return fragement;
    }

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

}
