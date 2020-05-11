package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.transportivo.R;
import com.example.transportivo.utils.FragmentHelper;

public class HomePageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentHelper fragmentHelper = new FragmentHelper(getFragmentManager());

        Button btnAddOffer = view.findViewById(R.id.btnAddOffer);
        Button btnNotifications = view.findViewById(R.id.btnNotifications);
        Button btnProfile = view.findViewById(R.id.btnProfile);
        Button btnReservations = view.findViewById(R.id.btnReservations);

        btnAddOffer.setOnClickListener(v -> fragmentHelper.switchToFragment(new AddOfferFragment()));
        btnNotifications.setOnClickListener(v -> fragmentHelper.switchToFragment(new NotificationFragment()));
        btnProfile.setOnClickListener(v -> fragmentHelper.switchToFragment(new ProfileFragment()));
        btnReservations.setOnClickListener(v -> fragmentHelper.switchToFragment(new ReservationsFragment()));
    }

}
