package com.example.raynold.saloonapp.Model;

import java.util.Date;

/**
 * Created by RAYNOLD on 8/29/2017.
 */

public class Appointment {

    private String mStartTime;
    private String mEndTime;
    private String mBook;
    private int mDay;

    public Appointment(String startTime, String endTime, String book ) {
        mStartTime = startTime;
        mEndTime = endTime;
        mBook = book;

    }

    public Appointment(int date) {
        mDay = date;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getBook() {
        return mBook;
    }

    public void setBook(String book) {
        mBook = book;
    }
}
