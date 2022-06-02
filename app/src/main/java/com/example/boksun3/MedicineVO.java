package com.example.boksun3;

public class MedicineVO {

    private int med_seq;
    private String user_id;
    private String med_box;
    private String med_name;
    private String med_hosp;
    private String med_way;
    private String med_times;
    private String med_date;
    private String med_alarm;
    private String med_update;
    private String med_memo;
    private String med_serial;

    public MedicineVO () {

    }

    public MedicineVO(int med_seq, String user_id, String med_box, String med_name, String med_hosp,
                      String med_way, String med_times, String med_date, String med_alarm, String med_update, String med_memo, String med_serial) {
        this.med_seq = med_seq;
        this.user_id = user_id;
        this.med_box = med_box;
        this.med_name = med_name;
        this.med_hosp = med_hosp;
        this.med_way = med_way;
        this.med_times = med_times;
        this.med_date = med_date;
        this.med_alarm = med_alarm;
        this.med_update = med_update;
        this.med_memo = med_memo;
        this.med_serial = med_serial;
    }

    @Override
    public String toString() {
        return "MedicineVO{" +
                "med_seq=" + med_seq +
                ", user_id='" + user_id + '\'' +
                ", med_box='" + med_box + '\'' +
                ", med_name='" + med_name + '\'' +
                ", med_hosp='" + med_hosp + '\'' +
                ", med_way='" + med_way + '\'' +
                ", med_times='" + med_times + '\'' +
                ", med_date='" + med_date + '\'' +
                ", med_alarm='" + med_alarm + '\'' +
                ", med_update='" + med_update + '\'' +
                ", med_memo='" + med_memo + '\'' +
                ", med_serial='" + med_serial + '\'' +
                '}';
    }

    public int getMed_seq() {
        return med_seq;
    }

    public void setMed_seq(int med_seq) {
        this.med_seq = med_seq;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMed_box() {
        return med_box;
    }

    public void setMed_box(String med_box) {
        this.med_box = med_box;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_hosp() {
        return med_hosp;
    }

    public void setMed_hosp(String med_hosp) {
        this.med_hosp = med_hosp;
    }

    public String getMed_way() {
        return med_way;
    }

    public void setMed_way(String med_way) {
        this.med_way = med_way;
    }

    public String getMed_times() {
        return med_times;
    }

    public void setMed_times(String med_times) {
        this.med_times = med_times;
    }

    public String getMed_date() {
        return med_date;
    }

    public void setMed_date(String med_date) {
        this.med_date = med_date;
    }

    public String getMed_alarm() {
        return med_alarm;
    }

    public void setMed_alarm(String med_alarm) {
        this.med_alarm = med_alarm;
    }

    public String getMed_update() {
        return med_update;
    }

    public void setMed_update(String med_update) {
        this.med_update = med_update;
    }

    public String getMed_memo() {
        return med_memo;
    }

    public void setMed_memo(String med_memo) {
        this.med_memo = med_memo;
    }

    public String getMed_serial() {
        return med_serial;
    }

    public void setMed_serial(String med_serial) {
        this.med_serial = med_serial;
    }
}
