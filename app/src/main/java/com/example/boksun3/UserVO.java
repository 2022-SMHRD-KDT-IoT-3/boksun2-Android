package com.example.boksun3;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

public class UserVO {

    private String user_id;
    private String user_name;
    private String user_birthdate;
    private String user_gender;
    private String user_joindate;
    private String user_addr;
    private String user_phone;
    private String worker_id;
    private String user_empn;
    private String user_access;
    private String user_disease;


    // 안드로이드에서만 쓰는 부분
    private String date;         // 최근 접속 날짜
    private String time;         // 최근 접속 시간
    private String condition;    // 환자 상태(상태에 따라 이미지값 매칭)


    // 부분 생성자 : 장애인 회원가입
    public UserVO(String user_id, String user_name, String user_joindate, String user_empn, String user_access, int i) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_joindate = user_joindate;
        this.user_empn = user_empn;
        this.user_access = user_access;

    }

    // 부분 생성자 : 장애인 상세보기


    public UserVO(String user_id, String user_name, String user_birthdate, String user_addr, String user_phone, String user_empn) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_addr = user_addr;
        this.user_phone = user_phone;
        this.user_empn = user_empn;
    }

    // 부분 생성자 : 복지사 장애인 등록
    public UserVO(String user_id, String user_name, String user_birthdate, String user_gender, String user_joindate,
                  String user_addr, String user_phone, String worker_id, String user_empn, String user_access, String user_disease) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_gender = user_gender;
        this.user_joindate = user_joindate;
        this.user_addr = user_addr;
        this.user_phone = user_phone;
        this.worker_id = worker_id;
        this.user_empn = user_empn;
        this.user_access = user_access;
        this.user_disease = user_disease;
    }


    // 부분 생성자 : SearchLifeAdapter
    public UserVO(String user_name, String user_addr, String date, String time, String condition) {
        this.user_name = user_name;
        this.user_addr = user_addr;
        this.date = date;
        this.time = time;
        this.condition = condition;
    }

    //부분생성 다민 adminlife 목록 date생성자 6
    public UserVO(String user_id, String user_name, String user_addr, String user_phone, String worker_id, String user_access,String user_disease ) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_addr = user_addr;
        this.user_phone = user_phone;
        this.worker_id = worker_id;
        this.user_access = user_access;
        this.user_disease = user_disease;
    }


    // 전체 생성자
    public UserVO(String user_id, String user_name, String user_birthdate, String user_gender, String user_joindate, String user_addr,
                  String user_phone, String worker_id, String user_empn, String user_access,  String date, String time, String condition, String user_disease) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_gender = user_gender;
        this.user_joindate = user_joindate;
        this.user_addr = user_addr;
        this.user_phone = user_phone;
        this.worker_id = worker_id;
        this.user_empn = user_empn;
        this.user_access = user_access;
        this.date = date;
        this.time = time;
        this.condition = condition;
        this.user_disease = user_disease;
    }


    @Override
    public String toString() {
        return "UserVO{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_birthdate='" + user_birthdate + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_joindate='" + user_joindate + '\'' +
                ", user_addr='" + user_addr + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", worker_id='" + worker_id + '\'' +
                ", user_empn='" + user_empn + '\'' +
                ", user_access='" + user_access + '\'' +
                ", user_disease='" + user_disease + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_birthdate() {
        return user_birthdate;
    }

    public void setUser_birthdate(String user_birthdate) {
        this.user_birthdate = user_birthdate;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_joindate() {
        return user_joindate;
    }

    public void setUser_joindate(String user_joindate) {
        this.user_joindate = user_joindate;
    }

    public String getUser_addr() {
        return user_addr;
    }

    public void setUser_addr(String user_addr) {
        this.user_addr = user_addr;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getUser_empn() {
        return user_empn;
    }

    public void setUser_empn(String user_empn) {
        this.user_empn = user_empn;
    }

    public String getUser_access() {
        return user_access;
    }

    public void setUser_access(String user_access) {
        this.user_access = user_access;
    }

    public String getUser_disease() {
        return user_disease;
    }

    public void setUser_disease(String user_disease) {
        this.user_disease = user_disease;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}