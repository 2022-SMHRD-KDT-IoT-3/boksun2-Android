package com.example.boksun3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentAdminUserList extends Fragment {
    private ListView listView;  // 검색을 보여줄 리스트변수
    private EditText editSearch; // 검색어를 입력할 Input 창

    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터

    private ArrayList<String> items = new ArrayList<>();  // 회원목록 list

    private ArrayList<String> arraylist;
    // 리스트의 모든 데이터를 arraylist에 복사한다.// items 복사본을 만든다.



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement = inflater.inflate(R.layout.fg_admin_userlist, container, false);

        editSearch = fragement.findViewById(R.id.edt_userserach);
        listView = fragement.findViewById(R.id.lv_userlist);

        //등록된 회원리스
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
        listView.setAdapter(adapter);

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

}