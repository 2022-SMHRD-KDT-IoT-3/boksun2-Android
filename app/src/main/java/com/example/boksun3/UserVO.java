package com.example.boksun3;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

public class UserVO {

    private String user_id;
    private String user_pw;
    private String user_name;
    private String user_birthdate;
    private String user_gender;
    private String user_joindate;
    private String user_addr;
    private String user_phone;
    private String worker_id;

    private String date;    // 최근 접속 날짜
    private String time;    // 최근 접속 시간
    private String condition; //환자 상태(상태에따라 이미지값 매칭)
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    //부분생성자 SearchLfieAdapter
    public UserVO(String user_name, String user_addr, String date, String time, String condition) {
        this.user_name = user_name;
        this.user_addr = user_addr;
        this.date = date;
        this.time = time;
        this.condition = condition;
    }

    // 시리얼 번호
    public UserVO(String user_id, String user_pw, String user_joindate) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_joindate = user_joindate;
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


    // 전체 생성자
    public UserVO(String user_id, String user_pw, String user_name, String user_birthdate, String user_gender, String user_joindate, String user_addr, String user_phone, String worker_id) {
        this.user_id = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.user_birthdate = user_birthdate;
        this.user_gender = user_gender;
        this.user_joindate = user_joindate;
        this.user_addr = user_addr;
        this.user_phone = user_phone;
        this.worker_id = worker_id;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "user_id='" + user_id + '\'' +
                ", user_pw='" + user_pw + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_birthdate='" + user_birthdate + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_joindate='" + user_joindate + '\'' +
                ", user_addr='" + user_addr + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", worker_id='" + worker_id + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
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
}
