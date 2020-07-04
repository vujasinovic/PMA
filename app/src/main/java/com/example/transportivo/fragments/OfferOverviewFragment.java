package com.example.transportivo.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.activity.PlacePickerActivity;
import com.example.transportivo.model.Notification;
import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.example.transportivo.provider.FirebaseClient;
import com.example.transportivo.services.LocationSharingService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class OfferOverviewFragment extends BaseFragment {
    private final int LOCATION_REQUEST_CODE = 1010;

    private final Map<OfferStatus, String> statusTextByOfferStatus;
    private final Map<OfferStatus, Integer> statusColorByOfferStatus;

    private static final String TAG = "OfferOverviewFragment";

    private Offer offer;
    private FirebaseClient<Offer> offerFirebaseClient = new FirebaseClient<>();

    public OfferOverviewFragment() {
        super(R.layout.offer_overview);
        statusTextByOfferStatus = new HashMap<>();
        statusColorByOfferStatus = new HashMap<>();

        statusTextByOfferStatus.put(OfferStatus.IN_PROGRESS, "In Progress");
        statusTextByOfferStatus.put(OfferStatus.CANCELED, "Canceled");
        statusTextByOfferStatus.put(OfferStatus.COMPLETED, "Completed");
        statusTextByOfferStatus.put(OfferStatus.RESERVED, "Reserved");

        statusColorByOfferStatus.put(OfferStatus.IN_PROGRESS, R.color.fui_bgTwitter);
        statusColorByOfferStatus.put(OfferStatus.CANCELED, R.color.design_default_color_error);
        statusColorByOfferStatus.put(OfferStatus.COMPLETED, R.color.primaryLight);
        statusColorByOfferStatus.put(OfferStatus.RESERVED, R.color.primaryDark);
    }

    @Override
    protected View initializeView(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        offer = args.getOffer();

        setupStatusBar(offer);
        setupButtons(offer);
        setupDescription(offer);
        setupAmountAndPrice(offer);
        setupDestinations(offer);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        final OfferStatus status = offer.getOfferStatus();
        final boolean isOwner = FirebaseAuth.getInstance().getUid().equals(offer.getOwner());

        getActivity().getMenuInflater().inflate(R.menu.offer_actions_menu, menu);

        final MenuItem itemCancel = menu.findItem(R.id.offer_cancel);
        final MenuItem itemTrace = menu.findItem(R.id.offer_trace);

        itemCancel.setOnMenuItemClickListener(this::cancel);
        itemTrace.setOnMenuItemClickListener(this::trace);

        if ((status == OfferStatus.OPEN && !isOwner) || status == OfferStatus.COMPLETED || status == OfferStatus.CANCELED) {
            itemCancel.setVisible(false);
        }

        itemTrace.setVisible(!isOwner && status == OfferStatus.IN_PROGRESS);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
    }

    private boolean trace(MenuItem menuItem) {
        Intent intent = new Intent(getContext(), PlacePickerActivity.class);
        intent.putExtra("longitude", offer.getLongitude());
        intent.putExtra("latitude", offer.getLatitude());
        intent.putExtra("readOnly", true);
        startActivity(intent);

        return true;
    }

    private void setupDestinations(Offer offer) {
        final TextView txtFrom = getView().findViewById(R.id.txtFrom);
        txtFrom.setOnClickListener(v -> displayOnMap(offer.getLocationFrom()));

        final TextView txtTo = getView().findViewById(R.id.txtTo);
        txtTo.setOnClickListener(v -> displayOnMap(offer.getLocationTo()));

        txtFrom.setText(offer.getLocationFrom());
        txtTo.setText(offer.getLocationTo());
    }

    private void displayOnMap(String address) {
        Intent intent = new Intent(getContext(), PlacePickerActivity.class);
        intent.putExtra("address", address);
        startActivityForResult(intent, 0);
    }

    private void setupAmountAndPrice(Offer offer) {
        final boolean accepted = isOfferAccepted(offer);
        final View container = getView().findViewById(R.id.amountAndPrice);
        final TextView capacity = getView().findViewById(R.id.reserverdAmount);
        final TextView price = getView().findViewById(R.id.price);

        container.setVisibility(accepted ? View.VISIBLE : View.GONE);
        price.setText("Price: " + offer.getPrice());
        capacity.setText("Capacity: " + offer.getCapacity() + "T");

        offerFirebaseClient.update(offer, res -> Log.i(TAG, "Successfully updated offer " + offer.getId()));
    }

    private boolean isOfferAccepted(Offer offer) {
        return offer.getOfferStatus().equals(OfferStatus.IN_PROGRESS);
    }

    private void setupDescription(Offer offer) {
        final TextView description = getView().findViewById(R.id.description);
        description.setText(offer.getDescription());

        TextView offeredBy = getView().findViewById(R.id.offeredBy);
        if (offer.getOwner().equals(FirebaseAuth.getInstance().getUid())) {
            offeredBy.setText("Your offer");
        }
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
        Button buttonStart = getView().findViewById(R.id.button_start);
        Button buttonComplete = getView().findViewById(R.id.button_complete);
        Button buttonRate = getView().findViewById(R.id.button_rate);

        buttonAccept.setVisibility(View.GONE);
        buttonStart.setVisibility(View.GONE);
        buttonComplete.setVisibility(View.GONE);
        buttonRate.setVisibility(View.GONE);

        buttonAccept.setOnClickListener(this::acceptOffer);
        buttonStart.setOnClickListener(this::startProgress);
        buttonComplete.setOnClickListener(this::complete);
        buttonRate.setOnClickListener(this::rate);

        final OfferStatus status = offer.getOfferStatus();
        final String currentUserId = FirebaseAuth.getInstance().getUid();
        final boolean isOwner = currentUserId.equals(offer.getOwner());

        if (status == OfferStatus.OPEN && !isOwner) {
            buttonAccept.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.RESERVED && isOwner) {
            buttonStart.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.IN_PROGRESS && isOwner) {
            buttonComplete.setVisibility(View.VISIBLE);
        } else if (status == OfferStatus.COMPLETED && offer.getClient().equals(currentUserId)) {
            buttonRate.setVisibility(View.VISIBLE);
        }
    }

    private void rate(View view) {
        final ReservationRatingFragmentArgs args = new ReservationRatingFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_reservation_rating, args.toBundle());
    }

    private void startProgress(View view) {
        offer.setOfferStatus(OfferStatus.IN_PROGRESS);
        offerFirebaseClient.update(offer, result -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startLocationSharing();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                // showInContextUI(...);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
            notify(offer.getClient(), "Delivery started", "Your order is in progress (" + offer.getLocationFrom() + " -> " + offer.getLocationTo() + ")");

            getActivity().onBackPressed();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && requestCode == requestCode) {
            startLocationSharing();
        }
    }

    private void startLocationSharing() {
        Intent intent = new Intent(getContext(), LocationSharingService.class);
        intent.putExtra("offer", offer);
        getActivity().startService(intent);
    }

    private void stopLocationSharing() {
        Intent intent = new Intent(getContext(), LocationSharingService.class);
        getActivity().stopService(intent);
    }

    private void complete(View view) {
        offer.setOfferStatus(OfferStatus.COMPLETED);

        offerFirebaseClient.update(offer, res -> {
            Log.i(TAG, "Successfully updated offer " + offer.getId() + " [COMPLETED]");

            notify(offer.getClient(), "Reservation completed", "Your reservation (" +
                    offer.getLocationFrom() + " -> " + offer.getLocationTo() + ") has been completed!");
            stopLocationSharing();
            getActivity().onBackPressed();
        });
    }

    private boolean cancel(MenuItem menuItem) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        final String currentUserId = FirebaseAuth.getInstance().getUid();
        final String currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        final boolean isCurrentUserOwner = offer.getOwner().equals(currentUserId);
        final String title = isCurrentUserOwner ? "Offer canceled" : "Reservation canceled";

        OfferStatus status = isCurrentUserOwner ? OfferStatus.CANCELED : OfferStatus.OPEN;
        String userToNotify = isCurrentUserOwner ? offer.getClient() : offer.getOwner();
        offer.setClient(null);
        offer.setOfferStatus(status);

        offerFirebaseClient.update(offer, res -> {
            Log.i(TAG, "Offer updated " + offer.getId() + " [" + status.name() + "]");
            notify(userToNotify, title, "User " + currentUserName +
                    " has canceled offer (" + offer.getLocationFrom() + " -> " + offer.getLocationTo() + ")");
            getActivity().onBackPressed();
        });

        return true;
    }

    private void acceptOffer(View view) {
        final OfferOverviewFragmentArgs args = OfferOverviewFragmentArgs.fromBundle(getArguments());
        final Offer offer = args.getOffer();
        final String currentUserName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        offer.setOfferStatus(OfferStatus.RESERVED);
        offer.setClient(FirebaseAuth.getInstance().getUid());
        offerFirebaseClient.update(offer, res -> {
                    Log.i(TAG, "Successfully updated offer " + offer.getId() + " [RESERVED]");
                    notify(offer.getOwner(), "Offer accepted", "User " + currentUserName +
                            " has accepted your offer (" + offer.getLocationFrom() + " -> " + offer.getLocationTo() + ")");

                    Navigation.findNavController(getView()).navigate(R.id.nav_reservations);
                }
        );
    }

    private void notify(String uid, String title, String body) {
        FirebaseClient<NotificationToken> tokenFirebaseClient = new FirebaseClient<>();
        Map<String, Object> query = new HashMap<>();
        query.put("owner", uid);

        tokenFirebaseClient.getAll(NotificationToken.class, query, res -> {
            Map<String, Object> data = new HashMap<>();
            data.put("title", title);
            data.put("userToken", res[0].getToken_id());
            data.put("body", body);
            data.put("push", true);
            FirebaseFunctions functions = FirebaseFunctions.getInstance();
            functions.getHttpsCallable("sendNotification").call(data);
        });

        FirebaseClient<Notification> notificationFirebaseClient = new FirebaseClient<>();
        Notification newNotification = new Notification(offer.getOwner(), title, body);
        notificationFirebaseClient.create(newNotification, o -> Log.i("CREATE NOTIFICATION", "Successfully created notification"));
    }

}
