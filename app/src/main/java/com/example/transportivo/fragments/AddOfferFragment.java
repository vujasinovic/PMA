package com.example.transportivo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.activity.PlacePickerActivity;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.provider.FirebaseClient;
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
    private static final String BASE_NAME = "AddOfferFragment";

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

    private void addOffer(View view) {
        TextInputEditText description = view.findViewById(R.id.txtDescription);
        TextInputEditText dateInput = view.findViewById(R.id.date);
        TextInputEditText departureInput = view.findViewById(R.id.departure);
        TextInputEditText arrivalInput = view.findViewById(R.id.arrivalTime);
        TextInputEditText capacityInput = view.findViewById(R.id.addCapacity);
        TextInputEditText priceInput = view.findViewById(R.id.addPrice);

        Offer offer = new Offer();
        offer.setDescription(description.getText().toString());
        offer.setLocationTo(locationTo.getText().toString());
        offer.setLocationFrom(locationFrom.getText().toString());
        offer.setDateTimeArrival(dateInput.getText().toString() + " " + arrivalInput.getText());
        offer.setDateTimeDeparture(dateInput.getText().toString() + " " + departureInput.getText());
        offer.setOfferStatus(OfferStatus.OPEN);
        offer.setPrice(priceInput.getText().toString());
        offer.setCapacity(capacityInput.getText().toString());

        FirebaseClient<Offer> firebaseClient = new FirebaseClient<>();
        firebaseClient.create(offer, o -> Log.i(BASE_NAME, "Successfully added new offer"));

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
