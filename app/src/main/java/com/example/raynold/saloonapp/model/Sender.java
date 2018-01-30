package com.example.raynold.saloonapp.model;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public class Sender {

    public String to;
    public Notification notification;

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }
}
