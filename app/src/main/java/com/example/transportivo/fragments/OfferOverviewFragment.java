package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.transportivo.R;

public class OfferOverviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        System.out.println("Display: " + args.getOffer()); //TODO

        return inflater.inflate(R.layout.offer_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: switch to fragment
/*
        final View.OnClickListener openMapOnClick =
                v -> fragmentHelper.switchToFragment(new MapFragment());
*/

//        view.findViewById(R.id.txtFrom).setOnClickListener(openMapOnClick);
//        view.findViewById(R.id.txtTo).setOnClickListener(openMapOnClick);
    }
}
