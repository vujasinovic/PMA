package com.example.transportivo.fragments;

import android.content.ContentValues;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.model.Reservation;
import com.example.transportivo.provider.OffersProvider;
import com.example.transportivo.provider.ReservationProvider;

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
        final TextView txtTo = getView().findViewById(R.id.txtTo);

        txtFrom.setText(offer.getLocationFrom());
        txtTo.setText(offer.getLocationTo());
    }

    private void setupAmountAndPrice(Offer offer) {
        ContentValues values = new ContentValues();
        final boolean accepted = checkStatus(offer);
        final View container = getView().findViewById(R.id.amountAndPrice);
        final TextView reservedAmount = getView().findViewById(R.id.reserverdAmount);
        final TextView price = getView().findViewById(R.id.price);
        container.setVisibility(accepted ? View.VISIBLE : View.GONE);
        price.setText("Price: 200$");
        reservedAmount.setText("Reserved 5T");
        values.put(Offer.Fields.price, price.toString());
        values.put(Offer.Fields.capacity, reservedAmount.toString());
        //TODO find id from offer
        values.put("id", "1");

        getContext().getContentResolver().update(OffersProvider.CONTENT_URI, values, "id", null);


    }

    private void setupDescription(Offer offer) {
        final TextView description = getView().findViewById(R.id.description);
        description.setText("A description for this offer will be displayed right here.");
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
        //TODO:  rate, comment
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        ContentValues values = new ContentValues();
        values.put(Offer.Fields.offerStatus, OfferStatus.COMPLETED.toString());
        values.put("id", offer.getId().toString());

        getContext().getContentResolver().update(OffersProvider.CONTENT_URI, values, "id", null);

        Navigation.findNavController(getView()).navigate(R.id.nav_reservations);

    }

    private void cancel(View view) {
        //TODO: cancel reservation (for user) or cancel offer (for transporter)
    }

    private void acceptOffer(View view) {

        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        ContentValues values = new ContentValues();
        values.put(Reservation.Fields.rating, 0);
        values.put(Reservation.Fields.comment, "");

        values.put("id", offer.getId().toString());
        getContext().getContentResolver().insert(ReservationProvider.CONTENT_URI, values);

        updateOffer(offer);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        NotificationCompat.Builder notification = notification();

        notificationManagerCompat.notify(1, notification.build());

    }

    private void updateOffer(Offer offer) {
        ContentValues values = new ContentValues();
        values.put(Offer.Fields.offerStatus, OfferStatus.IN_PROGRESS.toString());
        values.put("id", offer.getId().toString());

        getContext().getContentResolver().update(OffersProvider.CONTENT_URI, values, "id", null);
        Navigation.findNavController(getView()).navigate(R.id.nav_reservations);

    }

    private boolean checkStatus(Offer offer) {
        return offer.getOfferStatus().equals(OfferStatus.IN_PROGRESS) ? true : false;
    }

    private NotificationCompat.Builder notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "CHANEL")
                .setSmallIcon(R.drawable.ic_reservations)
                .setContentTitle("Reservation")
                .setContentText("You have one reservation by user jovanagrabez")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You have one reservation by user jovanagrabez"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder;
    }


}
