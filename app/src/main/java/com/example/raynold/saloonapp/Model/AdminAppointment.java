package com.example.raynold.saloonapp.Model;

/**
 * Created by RAYNOLD on 9/28/2017.
 */

public class AdminAppointment {

    private String name;
    private String date;
    private String startTime;
    private String endTime;
    private String email;
    private String userUid;

    public AdminAppointment() {}

    public AdminAppointment(String name, String date, String startTime, String endTime, String email, String userUid) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.email = email;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
