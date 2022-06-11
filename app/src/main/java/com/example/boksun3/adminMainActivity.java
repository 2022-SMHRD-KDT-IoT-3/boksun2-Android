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

    private long first_time;
    private long second_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_activity);

        // 복지사 로그인 정보
        WorkerVO wInfo = LoginCheck.wInfo;

        navi = findViewById(R.id.navi);
        fm = getSupportFragmentManager();
        life = new FragementAdminLife();
        userList = new FragmentAdminUserList();
        useradd = new FragementAdminUseradd();
        set = new FragementAdminSet();

        intent = getIntent();


        fm.beginTransaction().replace(R.id.frame, userList).commit();


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


    @Override
    public void onBackPressed(){
        fm.beginTransaction().replace(R.id.frame, userList).commit();

        second_time = System.currentTimeMillis();
        if(second_time - first_time < 1000){
            super.onBackPressed();
            finishAffinity();
        }
        first_time = System.currentTimeMillis();

    };


}


