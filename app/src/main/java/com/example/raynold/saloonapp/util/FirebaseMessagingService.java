package com.example.raynold.saloonapp.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.raynold.saloonapp.R;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by RAYNOLD on 11/19/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String clicked_action = remoteMessage.getNotification().getClickAction();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.hair)
                        .setContentTitle("Appointment")
                        .setContentText("An appointment has been booked");

        Intent intent = new Intent(clicked_action);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        int notificationId = (int) System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,builder.build());
    }
}
