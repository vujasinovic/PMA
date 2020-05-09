package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.transportivo.R;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.example.transportivo.ui.TransportivoTimePicker;
import com.google.android.material.textfield.TextInputLayout;

public class AddOfferFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();

        View view = inflater.inflate(R.layout.add_offer_fragment, container, false);

        setupListeners(view, activity);

        return view;
    }

    private void setupListeners(final View view, final FragmentActivity activity) {
        TextView departureInput = view.findViewById(R.id.departure);
        TextView arrivalInput = view.findViewById(R.id.arrivalTime);
        TextView dateInput = view.findViewById(R.id.date);

        TextInputLayout departureTime = view.findViewById(R.id.departureLayout);
        TextInputLayout arrivalTime = view.findViewById(R.id.arrivalTimeLayout);
        TextInputLayout date = view.findViewById(R.id.dateLayout);

        date.setEndIconOnClickListener(v ->
                TransportivoDatePicker.showAndSet(activity, dateInput)
        );

        departureTime.setEndIconOnClickListener(v ->
                TransportivoTimePicker.showAndSet(activity, departureInput, true)
        );

        arrivalTime.setEndIconOnClickListener(v ->
                TransportivoTimePicker.showAndSet(activity, arrivalInput, true)
        );
    }

}
