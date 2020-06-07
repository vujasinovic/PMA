package com.example.transportivo.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.provider.OffersProvider;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.example.transportivo.ui.TransportivoTimePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddOfferFragment extends BaseFragment {

    public AddOfferFragment() {
        super(R.layout.add_offer_fragment);
    }

    @Override
    protected View initializeView(View view) {
        final Activity activity = getActivity();

        final Button btnAddOffer = view.findViewById(R.id.btnAddOffer);
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

        btnAddOffer.setOnClickListener(o -> {
            addOffer(view);
        });

        return view;
    }

    public void addOffer(View view) {
        TextInputEditText description = view.findViewById(R.id.txtDescription);
        TextInputEditText locationFrom = view.findViewById(R.id.txtLocationFrom);
        TextInputEditText locationTo = view.findViewById(R.id.txtLocationTo);
        TextInputEditText dateInput = view.findViewById(R.id.date);
        TextInputEditText departureInput = view.findViewById(R.id.departure);
        TextInputEditText arrivalInput = view.findViewById(R.id.arrivalTime);

        ContentValues values = new ContentValues();
        values.put(Offer.Fields.locationFrom, locationFrom.getText().toString());
        values.put(Offer.Fields.locationTo, locationTo.getText().toString());
        values.put(Offer.Fields.dateTimeArrival, dateInput.getText().toString() + " " + arrivalInput.getText().toString());
        values.put(Offer.Fields.dateTimeDeparture, dateInput.getText().toString() + " " + departureInput.getText().toString());
        values.put(Offer.Fields.offerStatus, OfferStatus.OPEN.toString());

        Uri uri = getContext().getContentResolver().insert(OffersProvider.CONTENT_URI, values);
        Toast.makeText(getContext(), uri.toString(), Toast.LENGTH_LONG).show();

        Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
    }

}
