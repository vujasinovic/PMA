package com.example.transportivo.services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.transportivo.MainActivity;
import com.example.transportivo.R;
import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.provider.FirebaseClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {

    //when is app running


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSmallIcon(R.mipmap.ic_app_round)
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        int notification_id = (int) System.currentTimeMillis();
        notificationManagerCompat.notify(notification_id, notificationBuilder.build());
    }


    @Override
    public void onNewToken(String token) {
        Log.i("UPDATE TOKEN IN SERVICE", token);

    }

}
