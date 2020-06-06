package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.transportivo.R;

public class HomePageFragment extends BaseFragment {

    public HomePageFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAddOffer = view.findViewById(R.id.btnAddOffer);
        Button btnNotifications = view.findViewById(R.id.btnNotifications);
        Button btnProfile = view.findViewById(R.id.btnProfile);
        Button btnReservations = view.findViewById(R.id.btnReservations);

        //TODO
//        btnAddOffer.setOnClickListener(v -> fragmentHelper.switchToFragment(new AddOfferFragment()));
//        btnNotifications.setOnClickListener(v -> fragmentHelper.switchToFragment(new NotificationFragment()));
//        btnProfile.setOnClickListener(v -> fragmentHelper.switchToFragment(new ProfileFragment()));
//        btnReservations.setOnClickListener(v -> fragmentHelper.switchToFragment(new ReservationsFragment()));
    }

}
