package com.example.transportivo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;

import static androidx.core.content.ContextCompat.getSystemService;

public class TransportivoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(getApplicationContext(), "AIzaSyD0DD-YS_VUpZAgpBSUE2nxNAzvtoGFYsM");
        FirebaseApp.initializeApp(this);

    }


}
