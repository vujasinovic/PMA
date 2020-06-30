package com.example.transportivo;

import android.app.Application;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;

public class TransportivoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(getApplicationContext(), "AIzaSyAdTGCklXqnWZrlFrm991fhY_t7Kx8d9AA");
        FirebaseApp.initializeApp(this);

    }

}
