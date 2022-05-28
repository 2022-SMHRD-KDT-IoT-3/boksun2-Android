package com.example.boksun3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class adminMainActivity extends AppCompatActivity  {

    private BottomNavigationView navi;

    private FragmentManager fm;

    private FragementAdminLife life;
    private FragmentAdminUserList userList;
    private FragementAdminUseradd useradd;
    private FragementAdminSet set;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_activity);

        navi = findViewById(R.id.navi);
        fm = getSupportFragmentManager();
        life = new FragementAdminLife();
        userList = new FragmentAdminUserList();
        useradd = new FragementAdminUseradd();
        set = new FragementAdminSet();

        intent = getIntent();
        String menu = intent.getStringExtra("menu");
        Log.v("myData", menu); //어떤 버튼을 클릭해서 들어왔는지
        //String userlist = "userlist";

        if(menu.equals("userlist")){
            fm.beginTransaction().replace(R.id.frame, userList).commit();
        }else if(menu.equals("life")){
            fm.beginTransaction().replace(R.id.frame, life ).commit();
        }else if(menu.equals("useradd")){
            fm.beginTransaction().replace(R.id.frame, useradd ).commit();
        }else if(menu.equals("set")){
            fm.beginTransaction().replace(R.id.frame, set ).commit();
        }



        navi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.life_manage:
                        //fm.beginTransaction().replace(R.id.frame, info).commit();
                        fm.beginTransaction().replace(R.id.frame, life).commit();
                        Toast.makeText(getApplicationContext(), "생활관리", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_list:
                        fm.beginTransaction().replace(R.id.frame, userList).commit();
                        Toast.makeText(getApplicationContext(), "회원목록", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_add:
                        fm.beginTransaction().replace(R.id.frame, useradd).commit();
                        Toast.makeText(getApplicationContext(), "회원등록", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        fm.beginTransaction().replace(R.id.frame, set).commit();
                        Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

        });

    }
}


