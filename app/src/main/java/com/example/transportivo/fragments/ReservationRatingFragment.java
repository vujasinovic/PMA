package com.example.transportivo.fragments;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.Reservation;
import com.example.transportivo.provider.FirebaseClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ReservationRatingFragment extends BaseFragment {

    public ReservationRatingFragment() {
        super(R.layout.reservation_rating);
    }

    @Override
    protected View initializeView(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();

        Map<String, Object> query = new HashMap<>();
        query.put("offerId", offer.getId());

        AppCompatButton rateButton = view.findViewById(R.id.btnRateAndComplete);
        TextInputEditText comment = view.findViewById(R.id.txtComment);
        RatingBar rating = view.findViewById(R.id.ratingBar);

        FirebaseClient<Reservation> firebaseClient = new FirebaseClient<>();
        firebaseClient.getAll(Reservation.class, query, res -> {
            rateButton.setOnClickListener(l -> {
                Reservation reservation = res[0];
                String commentValue = comment.getText().toString();
                float ratingValue = rating.getRating();

                reservation.setComment(commentValue);
                reservation.setRating(ratingValue);

                firebaseClient.update(reservation, result -> {
                    Log.i("ReservationRatingFragment", "Reservation updated. Rating: " + result.getRating() + ", Comment: " + result.getComment());
                    Navigation.findNavController(getView()).navigate(R.id.nav_home);
                });
            });
        });

        return view;
    }
}
