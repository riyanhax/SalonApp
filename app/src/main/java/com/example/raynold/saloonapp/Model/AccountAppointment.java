package com.example.raynold.saloonapp.Model;

/**
 * Created by RAYNOLD on 9/11/2017.
 */

public class AccountAppointment {

    private String startTime;
    private String endTime;
    private String date;

    public AccountAppointment(String startTime, String endTime, String date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    AccountAppointment(){}

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
