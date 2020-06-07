package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.OffersAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class OfferFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View view;

    private final Offer[] offers = new Offer[]{
            new Offer("Mrkonjc", "Novi Sad", LocalDateTime.now().minusDays(1), LocalDateTime.now(), OfferStatus.OPEN),
            new Offer("Prilika", "Priboj", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), OfferStatus.OPEN)
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View response = nonNull(view) ? view : createView(inflater, container);
        return response;
    }

    private View createView(LayoutInflater inflater, @Nullable ViewGroup container) {
        view = inflater.inflate(R.layout.offers_fragment, container, false);

        recyclerView = view.findViewById(R.id.offers_res);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new OffersAdapter(offers, this::openOfferOverview);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

}
