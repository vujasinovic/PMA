package com.example.transportivo.fragments;

import android.view.View;
import android.widget.Button;

import com.example.transportivo.R;

public class HomePageFragment extends BaseFragment {

    public HomePageFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    protected View initializeView(View view) {
        Button btnAddOffer = view.findViewById(R.id.btnAddOffer);
        Button btnNotifications = view.findViewById(R.id.btnNotifications);
        Button btnProfile = view.findViewById(R.id.btnProfile);
        Button btnReservations = view.findViewById(R.id.btnReservations);

        return view;
    }
}
