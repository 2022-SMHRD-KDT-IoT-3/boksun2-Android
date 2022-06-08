package com.example.boksun3;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesManager {

    // 복지사용
    private static final String PREFERENCES_NAME = "pref";

    public static SharedPreferences getPreferences(Context mContext){
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        // PREFERENCES_NAME의 이름으로 사용(키-값 형태)
        // MODE_PRIVATE : 현재 앱에서만 접근 허락
    }

    // 로그아웃, 데이터 삭제
    public static void clearPreferences(Context context){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();  // 비동기 처리
    }

    // 로그인 정보 저장
    public static void setLoginInfo(Context context, String loginId, String loginPw){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("loginId", loginId);
        editor.putString("loginPw", loginPw);

        editor.apply();
    }

    // 로그인 정보 불러오기
    public static Map<String, String> getLoginInfo(Context context){
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> LoginInfo = new HashMap<>();
        String loginId = prefs.getString("loginId", "");
        String loginPw = prefs.getString("loginPw", "");



        return LoginInfo;
    }

}