package com.example.transportivo.fragments;

import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.OffersAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.provider.FirebaseClient;

import lombok.SneakyThrows;

public class OffersFragment extends BaseFragment {
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

        FirebaseClient<Offer> firebaseClient = new FirebaseClient<>();
        firebaseClient.getAll(Offer.class, result -> {
            adapter = new OffersAdapter(result, this::openOfferOverview);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        });

        return view;
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

}
