package com.example.transportivo.fragments;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.transportivo.R;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.example.transportivo.ui.TransportivoTimePicker;
import com.google.android.material.textfield.TextInputLayout;

public class AddOfferFragment extends BaseFragment {

    public AddOfferFragment() {
        super(R.layout.add_offer_fragment);
    }

    @Override
    protected View initializeView(View view) {
        final Activity activity = getActivity();

        final TextView departureInput = view.findViewById(R.id.departure);
        final TextView arrivalInput = view.findViewById(R.id.arrivalTime);
        final TextView dateInput = view.findViewById(R.id.date);

        final TextInputLayout departureTime = view.findViewById(R.id.departureLayout);
        final TextInputLayout arrivalTime = view.findViewById(R.id.arrivalTimeLayout);
        final TextInputLayout date = view.findViewById(R.id.dateLayout);

        date.setEndIconOnClickListener(v ->
                TransportivoDatePicker.showAndSet(activity, dateInput)
        );

        departureTime.setEndIconOnClickListener(v ->
                TransportivoTimePicker.showAndSet(activity, departureInput, true)
        );

        arrivalTime.setEndIconOnClickListener(v ->
                TransportivoTimePicker.showAndSet(activity, arrivalInput, true)
        );

        return view;
    }

}
