package com.example.transportivo.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.activity.PlacePickerActivity;
import com.example.transportivo.adapters.OffersAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.provider.FirebaseClient;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

public class SearchFragment extends BaseFragment {

    private static final int FROM_REQUEST_CODE = 1;
    private static final int TO_REQUEST_CODE = 2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextInputEditText locationFrom;
    private TextInputEditText locationTo;

    public SearchFragment() {
        super(R.layout.search);
    }

    @Override
    protected View initializeView(View view) {
        final Button btnAddOffer = view.findViewById(R.id.btnSearchOffer);
        locationFrom = view.findViewById(R.id.txtLocationFromSearch);
        locationFrom.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startPlaceAutocomplete(locationTo.getText().toString(), FROM_REQUEST_CODE);
            }
        });

        locationTo = view.findViewById(R.id.txtLocationToSearch);
        locationTo.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startPlaceAutocomplete(locationTo.getText().toString(), TO_REQUEST_CODE);
            }
        });

        btnAddOffer.setOnClickListener(v -> search(view));

        return view;
    }

    private void startPlaceAutocomplete(String address, int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setInitialQuery(address)
                .build(getContext());

        startActivityForResult(intent, requestCode);
    }

    private void search(View view) {
        recyclerView = view.findViewById(R.id.offers_res_search);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        Map<String, Object> query = new HashMap<>();
        query.put(Offer.Fields.locationFrom, locationFrom.getText().toString());
        query.put(Offer.Fields.locationTo, locationTo.getText().toString());
        query.put(Offer.Fields.offerStatus, OfferStatus.OPEN.toString());

        FirebaseClient<Offer> firebaseClient = new FirebaseClient<>();
        firebaseClient.getAll(Offer.class, query, result -> {
            adapter = new OffersAdapter(result, this::openOfferOverview);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        });
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
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
