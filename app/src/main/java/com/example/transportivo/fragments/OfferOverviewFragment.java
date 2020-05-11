package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.transportivo.R;
import com.example.transportivo.utils.FragmentHelper;

public class OfferOverviewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offer_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentHelper fragmentHelper = new FragmentHelper(getFragmentManager());
        final View.OnClickListener openMapOnClick =
                v -> fragmentHelper.switchToFragment(new MapFragment());

        view.findViewById(R.id.txtFrom).setOnClickListener(openMapOnClick);
        view.findViewById(R.id.txtTo).setOnClickListener(openMapOnClick);
    }
}
