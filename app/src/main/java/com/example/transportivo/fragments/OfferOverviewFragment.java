package com.example.transportivo.fragments;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.transportivo.R;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;

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
        final boolean accepted = false; //TODO: set to true if offer accepted (requires Offer modification)
        final View container = getView().findViewById(R.id.amountAndPrice);
        final TextView reservedAmount = getView().findViewById(R.id.reserverdAmount);
        final TextView price = getView().findViewById(R.id.price);

        container.setVisibility(accepted ? View.VISIBLE : View.GONE);
        price.setText("Price: 200$");
        reservedAmount.setText("Reserved 5T");
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
        if(nonNull(colorResource)) {
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
        final boolean accepted = false; //TODO: set to true if offer accepted (requires Offer modification)

        if (status == OfferStatus.OPEN && !accepted) {
            buttonAccept.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.OPEN && !accepted) {
            buttonCancel.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.IN_PROGRESS) {
            buttonComplete.setVisibility(View.VISIBLE);
        }
    }

    private void complete(View view) {
        //TODO: Mark reservation as completed, rate, comment
    }

    private void cancel(View view) {
        //TODO: cancel reservation (for user) or cancel offer (for transporter)
    }

    private void acceptOffer(View view) {
        //TODO: make reservation
    }

}
