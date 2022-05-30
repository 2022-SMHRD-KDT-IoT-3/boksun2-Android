package com.example.boksun3;

public class timeItem {

    int hour;
    int minute;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public timeItem(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "timeItem{" +
                "hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}


