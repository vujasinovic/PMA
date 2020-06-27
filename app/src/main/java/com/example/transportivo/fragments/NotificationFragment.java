package com.example.transportivo.fragments;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.NotificationAdapter;
import com.example.transportivo.adapters.OffersAdapter;
import com.example.transportivo.model.Notification;
import com.example.transportivo.model.Offer;
import com.example.transportivo.provider.FirebaseClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;

public class NotificationFragment extends BaseFragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFunctions firebaseFunctions;


    public NotificationFragment() {
        super(R.layout.notification_fragment);
        firebaseFunctions = FirebaseFunctions.getInstance();
    }

    @Override
    protected View initializeView(View view) {

        final RecyclerView recyclerView = view.findViewById(R.id.notifications);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String tokenId = FirebaseAuth.getInstance().getUid();
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        Map<String, Object> query = new HashMap<>();
        query.put("UID", tokenId);

        FirebaseClient<Notification> firebaseClient = new FirebaseClient<>();
        firebaseClient.getAll(Notification.class, query, result -> {
            adapter = new NotificationAdapter(result);
            recyclerView.setAdapter(adapter);
        });


        return view;
    }

}
