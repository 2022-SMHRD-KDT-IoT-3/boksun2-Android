package com.example.boksun3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class adminUserList extends AppCompatActivity {
    MenuItem mSearch;
    private BottomNavigationView navi;
    //private Fragmentinfo info;
    private FragmentManager fm;
   // private FragmentList list;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        navi = findViewById(R.id.navi);
        //info = new Fragmentinfo();
        fm = getSupportFragmentManager();
        //list = new FragmentList();
        intent = getIntent();
        String menu = intent.getStringExtra("menu");
        Log.v("myData",menu);

        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.life_manage:
                        //fm.beginTransaction().replace(R.id.frame, info).commit();
                        Toast.makeText(getApplicationContext(),"생활관리",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_list:
                        //fm.beginTransaction().replace(R.id.frame, list).commit();
                        Toast.makeText(getApplicationContext(),"회원목록",Toast.LENGTH_SHORT).show();
                        break;
                    case  R.id.user_add:
                        Toast.makeText(getApplicationContext(),"회원등록",Toast.LENGTH_SHORT).show();
                    case  R.id.settings:
                        Toast.makeText(getApplicationContext(),"설정",Toast.LENGTH_SHORT).show();

                }
                return true;
            }

        });

    }


    //상단바 검색 메뉴 생성하는 onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        String search = intent.getStringExtra("menu");
        Log.v("myData", search);
        String type = "userlist"; //userlist일 경우에만 검색 바 나타나게

        if (search.equals(type)) {
            //search_user.xml 등록
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.search_user, menu);

            MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    Toast.makeText(adminUserList.this, "검색기능실행", Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    Toast.makeText(adminUserList.this, "이거뭐임", Toast.LENGTH_SHORT).show();
                    return true;
                }
            };
            menu.findItem(R.id.search).setOnActionExpandListener(onActionExpandListener);
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setQueryHint("이름 검색");

        }
        return true;
    }
}

