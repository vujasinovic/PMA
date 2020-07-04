package com.example.transportivo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.provider.FirebaseClient;
import com.example.transportivo.provider.NotificationTokenProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class NotificationTokenUploader extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
            final boolean networkAvailable = mobNetInfo.isAvailable();

            if (networkAvailable) {
                final Cursor cursor = context.getContentResolver().query(NotificationTokenProvider.CONTENT_URI, null, null, null, null);
                if (cursor.moveToLast()) {
                    final String token = cursor.getString(cursor.getColumnIndex("token_id"));
                    uploadToken(token);
                }
            }
        } catch (Exception e) {
        }
    }

    private void uploadToken(String token) {
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