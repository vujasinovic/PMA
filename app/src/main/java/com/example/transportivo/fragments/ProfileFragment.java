package com.example.transportivo.fragments;

import android.view.View;

import com.example.transportivo.R;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends BaseFragment {

    public ProfileFragment() {
        super(R.layout.profile_fragment);
    }

    @Override
    protected View initializeView(View view) {
        TextInputLayout dateInputHolder = view.findViewById(R.id.dateInputHolder);
        TextInputEditText dateInput = view.findViewById(R.id.dateInput);

        dateInputHolder.setEndIconOnClickListener(v -> TransportivoDatePicker
                .showAndSet(getActivity(), dateInput));
        return view;
    }

}
