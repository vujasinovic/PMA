package com.example.transportivo.fragments;

import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.OffersAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class OffersFragment extends BaseFragment {
    private static final String OFFER_COLLECTION_NAME = "offer";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public OffersFragment() {
        super(R.layout.offers_fragment);
    }

    @SneakyThrows
    @Override
    protected View initializeView(View view) {
        recyclerView = view.findViewById(R.id.offers_res);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(OFFER_COLLECTION_NAME)
                .whereEqualTo(Offer.Fields.offerStatus, OfferStatus.OPEN.toString())
                .get()
                .addOnCompleteListener(l -> {
                            List<Offer> offers = l.getResult().getDocuments().stream()
                                    .map(d -> objectMapper.convertValue(d.getData(), Offer.class).withId(d.getId()))
                                    .collect(Collectors.toList());

                            Offer[] offersArray = objectMapper.convertValue(offers, Offer[].class);

                            adapter = new OffersAdapter(offersArray, this::openOfferOverview);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        }
                );
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

}
