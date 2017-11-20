package com.example.raynold.saloonapp.model;

/**
 * Created by RAYNOLD on 8/29/2017.
 */

public class Appointment {

    private String mStartTime;
    private String mEndTime;
    private String mBook;
    private String mDate;

    public Appointment(){}

    public Appointment(String startTime, String endTime, String book ) {
        mStartTime = startTime;
        mEndTime = endTime;
        mBook = book;

    }

    public Appointment(String date) {
        mDate = date;
    }

    public String getDay() {
        return mDate;
    }

    public void setDay(String day) {
        mDate = day;
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
