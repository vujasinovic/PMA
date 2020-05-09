package com.example.transportivo.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.transportivo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddOfferFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_offer_fragment, container, false);


        TextInputLayout departureTime = (TextInputLayout) view.findViewById(R.id.departureLayout);
        TextInputLayout arrivalTime = (TextInputLayout) view.findViewById(R.id.arrivalTimeLayout);
        TextInputLayout date = (TextInputLayout) view.findViewById(R.id.dateLayout);
        departureTime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment(mTimeSetListener);
                timePicker.show(getFragmentManager().beginTransaction(), "time picker");
            }
        });


        arrivalTime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment(TimeSetListener);
                timePicker.show(getFragmentManager().beginTransaction(), "time picker");
            }
        });

        date.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return view;
    }


    TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            TextInputEditText textView = (TextInputEditText) getActivity().findViewById(R.id.departure);
            textView.setText(hourOfDay + ":" + minute);
        }
    };

    TimePickerDialog.OnTimeSetListener TimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            TextInputEditText textView = (TextInputEditText) getActivity().findViewById(R.id.arrivalTime);
            textView.setText(hourOfDay + ":" + minute);
        }
    };

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextInputEditText textView = (TextInputEditText) getActivity().findViewById(R.id.date);
        textView.setText(month + "/" + dayOfMonth + "/" + year);
    }
}
