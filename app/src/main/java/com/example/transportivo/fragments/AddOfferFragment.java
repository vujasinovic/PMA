package com.example.transportivo.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.activity.PlacePickerActivity;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.provider.OffersProvider;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.example.transportivo.ui.TransportivoTimePicker;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

public class AddOfferFragment extends BaseFragment {
    private static final int FROM_REQUEST_CODE = 1;
    private static final int TO_REQUEST_CODE = 2;

    private TextInputEditText locationFrom;
    private TextInputEditText locationTo;


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

        btnAddOffer.setOnClickListener(o -> addOffer(view));

        locationFrom = view.findViewById(R.id.txtLocationFrom);
        locationFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startPlaceAutocomplete(locationTo.getText().toString(), FROM_REQUEST_CODE);
            }
        });

        locationTo = view.findViewById(R.id.txtLocationTo);
        locationTo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startPlaceAutocomplete(locationTo.getText().toString(), TO_REQUEST_CODE);
            }
        });

        view.findViewById(R.id.fromDestinationPin).setOnClickListener(v -> {
            startPlacePicker(locationFrom.getText().toString(), FROM_REQUEST_CODE);
        });
        view.findViewById(R.id.toDestinationPin).setOnClickListener(v -> {
            startPlacePicker(locationTo.getText().toString(), TO_REQUEST_CODE);
        });

        return view;
    }

    private void startPlacePicker(String currentAddress, int requestCode) {
        Intent intent = new Intent(getContext(), PlacePickerActivity.class);
        intent.setData(Uri.parse(currentAddress));
        startActivityForResult(intent, requestCode);
    }

    private void startPlaceAutocomplete(String address, int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setInitialQuery(address)
                .build(getContext());

        startActivityForResult(intent, requestCode);
    }

    public void addOffer(View view) {
        TextInputEditText description = view.findViewById(R.id.txtDescription);
        TextInputEditText dateInput = view.findViewById(R.id.date);
        TextInputEditText departureInput = view.findViewById(R.id.departure);
        TextInputEditText arrivalInput = view.findViewById(R.id.arrivalTime);
        TextInputEditText capacityInput = view.findViewById(R.id.addCapacity);
        TextInputEditText priceInput = view.findViewById(R.id.addPrice);

        ContentValues values = new ContentValues();
        values.put(Offer.Fields.locationFrom, locationFrom.getText().toString());
        values.put(Offer.Fields.locationTo, locationTo.getText().toString());
        values.put(Offer.Fields.dateTimeArrival, dateInput.getText().toString() + " " + arrivalInput.getText());
        values.put(Offer.Fields.dateTimeDeparture, dateInput.getText().toString() + " " + departureInput.getText());
        values.put(Offer.Fields.offerStatus, OfferStatus.OPEN.toString());
        values.put(Offer.Fields.price, priceInput.getText().toString());
        values.put(Offer.Fields.capacity, capacityInput.getText().toString());
        values.put(Offer.Fields.description, description.getText().toString());

        Uri uri = getContext().getContentResolver().insert(OffersProvider.CONTENT_URI, values);

        Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String address = null;
        if (resultCode == AutocompleteActivity.RESULT_OK) {
            final Place place = Autocomplete.getPlaceFromIntent(data);
            address = place.getAddress();
        } else if (resultCode == PlacePickerActivity.RESULT_OK) {
            address = data.getDataString();
        }

        if (nonNull(address)) {
            if (requestCode == FROM_REQUEST_CODE) {
                locationFrom.setText(address);
            } else if (requestCode == TO_REQUEST_CODE) {
                locationTo.setText(address);
            }
        }

        locationFrom.clearFocus();
        locationTo.clearFocus();
    }


}
