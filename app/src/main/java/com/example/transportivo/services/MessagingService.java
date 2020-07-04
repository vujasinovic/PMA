package com.example.transportivo.services;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.transportivo.R;
import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.provider.FirebaseClient;
import com.example.transportivo.provider.NotificationTokenProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MessagingService extends FirebaseMessagingService {

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
        uploadToken(token);

        ContentValues contentValues = new ContentValues();
        contentValues.put("token_id", token);

        getApplicationContext().getContentResolver().insert(NotificationTokenProvider.CONTENT_URI, contentValues);
    }

    private void uploadToken(String token) {
        SharedPreferences.Editor editor = getSharedPreferences("TOKEN_PREF", MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();

        NotificationToken notificationToken = new NotificationToken();
        notificationToken.setToken_id(token);
        FirebaseClient<NotificationToken> firebaseClient = new FirebaseClient<>();
        Map<String, Object> query = new HashMap<>();
        query.put("owner", FirebaseAuth.getInstance().getUid());
        firebaseClient.getAll(NotificationToken.class, query, result -> {
            if (result.length > 0) {
                result[0].setToken_id(FirebaseInstanceId.getInstance().getToken());
                firebaseClient.update(result[0], o -> Log.i("UPDATE TOKEN", "Successfully updated token"));
            } else {
                firebaseClient.create(notificationToken, o -> Log.i("CREATE TOKEN", "Successfully added new offer"));
            }
        });
    }


}
