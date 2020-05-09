package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.transportivo.R;
import com.example.transportivo.utils.DatePickerUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();

        final View view = inflater.inflate(R.layout.profile_fragment, container, false);

        TextInputLayout dateInputHolder = view.findViewById(R.id.dateInputHolder);
        TextInputEditText dateInput = view.findViewById(R.id.dateInput);

        dateInputHolder.setEndIconOnClickListener(v -> DatePickerUtils.showAndSet(activity, dateInput));

        return view;
    }

}
