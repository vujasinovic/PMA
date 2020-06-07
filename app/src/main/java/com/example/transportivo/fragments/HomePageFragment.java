package com.example.transportivo.fragments;

import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.example.transportivo.R;

public class HomePageFragment extends BaseFragment {

    public HomePageFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    protected View initializeView(View view) {
        final Button btnAddOffer = view.findViewById(R.id.btnAddOffer);
        btnAddOffer.setOnClickListener(v -> openFragment(R.id.nav_add_offer));

        final Button btnNotifications = view.findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(v -> openFragment(R.id.nav_notifications));

        final Button btnProfile = view.findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> openFragment(R.id.nav_profile));

        final Button btnReservations = view.findViewById(R.id.btnReservations);
        btnReservations.setOnClickListener(v -> openFragment(R.id.nav_reservations));
        return view;
    }

    private void openFragment(int fragment) {
        Navigation.findNavController(getView()).navigate(fragment);
    }

}
