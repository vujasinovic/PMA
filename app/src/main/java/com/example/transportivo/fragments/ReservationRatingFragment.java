package com.example.transportivo.fragments;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;

import com.example.transportivo.R;
import com.example.transportivo.model.Comment;
import com.example.transportivo.model.Notification;
import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.Reservation;
import com.example.transportivo.provider.FirebaseClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;

public class ReservationRatingFragment extends BaseFragment {

    private FirebaseUser user;

    public ReservationRatingFragment() {
        super(R.layout.reservation_rating);
        user = FirebaseAuth.getInstance().getCurrentUser();
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
                    Log.i("ReservationRatingFr", "Reservation updated. Rating: " + result.getRating() + ", Comment: " + result.getComment());
                    Navigation.findNavController(getView()).navigate(R.id.nav_home);
                });
                getNotification(view, offer, ratingValue);
            });

        });


        return view;
    }


    private void getNotification(View view, Offer offer, float ratingValue) {
        FirebaseClient<NotificationToken> tokenFirebaseClient = new FirebaseClient<>();
        Map<String, Object> query = new HashMap<>();
        query.put("owner", offer.getOwner());
        tokenFirebaseClient.getAll(NotificationToken.class, query, res -> {
            Map<String, Object> data = new HashMap<>();
            data.put("title", "Comment and Rating");
            data.put("userToken", res[0].getToken_id());
            data.put("body", "User" + user.getDisplayName() + "set comment and rate for you");
            data.put("push", true);
            FirebaseFunctions functions = FirebaseFunctions.getInstance();
            functions.getHttpsCallable("sendNotification").call(data);


        });

        TextInputEditText comment = view.findViewById(R.id.txtComment);

        FirebaseClient<Comment> commentFirebaseClient = new FirebaseClient<>();
        Comment newComment = new Comment(user.getDisplayName(), offer.getOwner(), comment.getText().toString(), ratingValue);
        commentFirebaseClient.create(newComment, o -> Log.i("CREATE COMMENT", "Successfully created comment"));

        FirebaseClient<Notification> notificationFirebaseClient = new FirebaseClient<>();
        Notification newNotification = new Notification(offer.getOwner(), "Comment and Rate", user.getDisplayName() + " set comment: ".concat(comment.getText().toString()) + " and rate:".concat(String.valueOf(ratingValue)));
        notificationFirebaseClient.create(newNotification, o -> Log.i("CREATE NOTIFICATION", "Successfully created notification"));

    }
}
