package com.example.transportivo.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.transportivo.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddOfferFragment extends Fragment implements TimePickerDialog.OnTimeSetListener  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_offer_fragment, container, false);

        //TODO: add timePicker and datePicker
    /*    Button button = (Button) view.findViewById(R.id.timePicker1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager().beginTransaction(),"time picker");
            }
        });*/

        return view;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextInputEditText textView = (TextInputEditText) getActivity().findViewById(R.id.departure);
        textView.setText(  hourOfDay + ":" + minute);
    }
}
