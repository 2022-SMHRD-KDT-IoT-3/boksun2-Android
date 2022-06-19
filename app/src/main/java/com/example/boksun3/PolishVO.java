package com.example.boksun3;

public class PolishVO {
    private String notice_seq;
    private String notice_type;
    private String worker_id;
    private String notice_title;
    private String notice_content;
    private String notice_date;
    private String notice_cnt;
    private String notice_chk;

    public PolishVO(String notice_seq, String notice_type, String worker_id, String notice_title, String notice_content, String notice_date, String notice_cnt, String notice_chk) {
        this.notice_seq = notice_seq;
        this.notice_type = notice_type;
        this.worker_id = worker_id;
        this.notice_title = notice_title;
        this.notice_content = notice_content;
        this.notice_date = notice_date;
        this.notice_cnt = notice_cnt;
        this.notice_chk = notice_chk;
    }
    public String getNotice_seq() {
        return notice_seq;
    }

    public void setNotice_seq(String notice_seq) {
        this.notice_seq = notice_type;
    }

    public String getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(String notice_type) {
        this.notice_type = notice_type;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_contents() {
        return notice_content;
    }

    public void setNotice_contents(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }

    public String getNotice_cnt() {
        return notice_cnt;
    }

    public void setNotice_cnt(String notice_cnt) {
        this.notice_cnt = notice_cnt;
    }

    public String getNotice_chk() {
        return notice_chk;
    }

    public void setNotice_chk(String notice_chk) {
        this.notice_chk = notice_chk;
    }
}
