package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.ActiveReservationAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActiveOffersFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.active_reservation_fragment, container, false);
        recyclerView = view.findViewById(R.id.active_res);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("offer")
                .whereIn(Offer.Fields.offerStatus, Arrays.asList(OfferStatus.IN_PROGRESS.toString(), OfferStatus.RESERVED.toString()))
                .get()
                .addOnCompleteListener(l -> {
                            List<Offer> offers = l.getResult().getDocuments().stream()
                                    .map(d -> objectMapper.convertValue(d.getData(), Offer.class).withId(d.getId()))
                                    .collect(Collectors.toList());

                            Offer[] offersArray = objectMapper.convertValue(offers, Offer[].class);

                            adapter = new ActiveReservationAdapter(offersArray, this::openOfferOverview);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
                );

        return view;
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

}
