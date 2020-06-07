package com.example.transportivo.fragments;

import android.view.View;

import com.example.transportivo.R;

public class OfferOverviewFragment extends BaseFragment {

    public OfferOverviewFragment() {
        super(R.layout.offer_overview);
    }

    @Override
    protected View initializeView(View view) {
        OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        System.out.println("Display: " + args.getOffer()); //TODO

        return view;
    }

}
