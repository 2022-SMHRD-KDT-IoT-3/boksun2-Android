package com.example.boksun3;

public class WorkerVO {

    private String worker_id;
    private String worker_pw;
    private String worker_name;
    private String worker_license;
    private String worker_organization;
    private String worker_tel;
    private String worker_phone;
    private String worker_area;
    private String woker_joindate;

    public WorkerVO() {
    }

    public WorkerVO(String worker_id, String worker_pw, String worker_name, String worker_license,
                    String worker_organization, String worker_tel, String worker_phone, String worker_area, String woker_joindate) {
        this.worker_id = worker_id;
        this.worker_pw = worker_pw;
        this.worker_name = worker_name;
        this.worker_license = worker_license;
        this.worker_organization = worker_organization;
        this.worker_tel = worker_tel;
        this.worker_phone = worker_phone;
        this.worker_area = worker_area;
        this.woker_joindate = woker_joindate;
    }

    @Override
    public String toString() {
        return "WorkerVO{" +
                "worker_id='" + worker_id + '\'' +
                ", worker_pw='" + worker_pw + '\'' +
                ", worker_name='" + worker_name + '\'' +
                ", worker_license='" + worker_license + '\'' +
                ", worker_organization='" + worker_organization + '\'' +
                ", worker_tel='" + worker_tel + '\'' +
                ", worker_phone='" + worker_phone + '\'' +
                ", worker_area='" + worker_area + '\'' +
                ", woker_joindate='" + woker_joindate + '\'' +
                '}';
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_pw() {
        return worker_pw;
    }

    public void setWorker_pw(String worker_pw) {
        this.worker_pw = worker_pw;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_license() {
        return worker_license;
    }

    public void setWorker_license(String worker_license) {
        this.worker_license = worker_license;
    }

    public String getWorker_organization() {
        return worker_organization;
    }

    public void setWorker_organization(String worker_organization) {
        this.worker_organization = worker_organization;
    }

    public String getWorker_tel() {
        return worker_tel;
    }

    public void setWorker_tel(String worker_tel) {
        this.worker_tel = worker_tel;
    }

    public String getWorker_phone() {
        return worker_phone;
    }

    public void setWorker_phone(String worker_phone) {
        this.worker_phone = worker_phone;
    }

    public String getWorker_area() {
        return worker_area;
    }

    public void setWorker_area(String worker_area) {
        this.worker_area = worker_area;
    }

    public String getWoker_joindate() {
        return woker_joindate;
    }

    public void setWoker_joindate(String woker_joindate) {
        this.woker_joindate = woker_joindate;
    }
}
