package com.example.transportivo.fragments;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.activity.PlacePickerActivity;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.model.Reservation;
import com.example.transportivo.provider.FirebaseClient;

import java.util.Map;

import static com.google.android.gms.common.util.CollectionUtils.mapOf;
import static java.util.Objects.nonNull;

public class OfferOverviewFragment extends BaseFragment {
    private static final Map<OfferStatus, String> statusTextByOfferStatus = mapOf(
            OfferStatus.IN_PROGRESS, "In Progress",
            OfferStatus.CANCELED, "Canceled",
            OfferStatus.COMPLETED, "Completed");

    private static final Map<OfferStatus, Integer> statusColorByOfferStatus = mapOf(
            OfferStatus.IN_PROGRESS, R.color.fui_bgTwitter,
            OfferStatus.CANCELED, R.color.design_default_color_error,
            OfferStatus.COMPLETED, R.color.primaryLight);

    private static final String EMPTY = "";
    private static final String TAG = "OfferOverviewFragment";

    public OfferOverviewFragment() {
        super(R.layout.offer_overview);
    }

    @Override
    protected View initializeView(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        setupStatusBar(offer);
        setupButtons(offer);
        setupDescription(offer);
        setupAmountAndPrice(offer);
        setupDestinations(offer);

        return view;
    }

    private void setupDestinations(Offer offer) {
        final TextView txtFrom = getView().findViewById(R.id.txtFrom);
        txtFrom.setOnClickListener(v -> {
            displayOnMap(offer.getLocationFrom());
        });

        final TextView txtTo = getView().findViewById(R.id.txtTo);
        txtTo.setOnClickListener(v -> {
            displayOnMap(offer.getLocationTo());
        });

        txtFrom.setText(offer.getLocationFrom());
        txtTo.setText(offer.getLocationTo());
    }

    private void displayOnMap(String address) {
        Intent intent = new Intent(getContext(), PlacePickerActivity.class);
        intent.setData(Uri.parse(address));
        startActivityForResult(intent, 0);
    }

    private void setupAmountAndPrice(Offer offer) {
        final boolean accepted = isOfferAccepted(offer);
        final View container = getView().findViewById(R.id.amountAndPrice);
        final TextView capacity = getView().findViewById(R.id.reserverdAmount);
        final TextView price = getView().findViewById(R.id.price);

        container.setVisibility(accepted ? View.VISIBLE : View.GONE);
        price.setText(offer.getPrice());
        capacity.setText(offer.getCapacity());

        FirebaseClient<Offer> offerFirebaseClient = new FirebaseClient<>();
        offerFirebaseClient.update(offer, res -> Log.i(TAG, "Successfully updated offer " + offer.getId()));
    }

    private boolean isOfferAccepted(Offer offer) {
        return offer.getOfferStatus().equals(OfferStatus.IN_PROGRESS);
    }

    private void setupDescription(Offer offer) {
        final TextView description = getView().findViewById(R.id.description);
        description.setText(offer.getDescription());
    }

    private void setupStatusBar(Offer offer) {
        final TextView statusBar = getView().findViewById(R.id.status);
        final OfferStatus status = offer.getOfferStatus();
        int visibility = status == OfferStatus.OPEN ? View.GONE : View.VISIBLE;

        statusBar.setVisibility(visibility);
        statusBar.setText(statusTextByOfferStatus.get(status));

        Integer colorResource = statusColorByOfferStatus.get(status);
        if (nonNull(colorResource)) {
            statusBar.setBackgroundColor(ContextCompat.getColor(getContext(), colorResource));
        }
    }

    private void setupButtons(Offer offer) {
        Button buttonAccept = getView().findViewById(R.id.button_accept);
        Button buttonCancel = getView().findViewById(R.id.button_cancel);
        Button buttonComplete = getView().findViewById(R.id.button_complete);

        buttonAccept.setVisibility(View.GONE);
        buttonCancel.setVisibility(View.GONE);
        buttonComplete.setVisibility(View.GONE);

        buttonAccept.setOnClickListener(this::acceptOffer);
        buttonCancel.setOnClickListener(this::cancel);
        buttonComplete.setOnClickListener(this::complete);

        final OfferStatus status = offer.getOfferStatus();
        final boolean accepted = checkStatus(offer);

        if (status == OfferStatus.OPEN && !accepted) {
            buttonAccept.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.IN_PROGRESS) {
            buttonComplete.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.GONE);
        }
    }

    private void complete(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        Offer offer = args.getOffer();
        offer.setOfferStatus(OfferStatus.COMPLETED);

        FirebaseClient<Offer> offerFirebaseClient = new FirebaseClient<>();
        offerFirebaseClient.update(offer, res -> {
            Log.i(TAG, "Successfully updated offer " + offer.getId() + " [COMPLETED]");

            final ReservationRatingFragmentArgs reservationRatingArgs = new ReservationRatingFragmentArgs.Builder(offer).build();
            Navigation.findNavController(getView()).navigate(R.id.nav_reservation_rating, reservationRatingArgs.toBundle());

//            Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
        });
    }

    private void cancel(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        Offer offer = args.getOffer();
        offer.setOfferStatus(OfferStatus.CANCELED);

        FirebaseClient<Offer> offerFirebaseClient = new FirebaseClient<>();
        offerFirebaseClient.update(offer, res -> {
            Log.i(TAG, "Successfully updated offer " + offer.getId() + " [CANCELED]");
            Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
        });
    }

    private void acceptOffer(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        final Reservation reservation = new Reservation();

        reservation.setComment("");
        reservation.setRating(0f);
        reservation.setOfferId(offer.getId());

        FirebaseClient<Reservation> reservationFirebaseClient = new FirebaseClient<>();
        reservationFirebaseClient.create(reservation, result ->
                Log.i(TAG, "Successfully created new reservation")
        );

        FirebaseClient<Offer> offerFirebaseClient = new FirebaseClient<>();
        offer.setOfferStatus(OfferStatus.IN_PROGRESS);
        offerFirebaseClient.update(offer, res -> {
                    Log.i(TAG, "Successfully updated offer " + offer.getId() + " [IN PROGRESS]");
                    Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
                }
        );

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        NotificationCompat.Builder notification = notification();

        notificationManagerCompat.notify(1, notification.build());
    }

    private boolean checkStatus(Offer offer) {
        return offer.getOfferStatus().equals(OfferStatus.IN_PROGRESS);
    }

    private NotificationCompat.Builder notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "CHANEL")
                .setSmallIcon(R.drawable.ic_reservations)
                .setContentTitle("Reservation")
                .setContentText("Reservation status was changed")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Reservation status was changed"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;
    }

}
