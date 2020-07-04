package com.example.transportivo;

import android.app.Application;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class TransportivoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Places.initialize(getApplicationContext(), "AIzaSyAdTGCklXqnWZrlFrm991fhY_t7Kx8d9AA");
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }

}
