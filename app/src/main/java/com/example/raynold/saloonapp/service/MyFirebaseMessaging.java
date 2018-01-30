package com.example.raynold.saloonapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.activity.MainActivity;
import com.example.raynold.saloonapp.util.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id");
        builder.setSmallIcon(R.drawable.lumo_logo)
                .setContentIntent(pendingIntent)
                .setContentText(notification.getTitle())
                .setAutoCancel(true);


        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }
}
