package com.example.transportivo.fragments;

import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.HistoryReservationAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;

import java.time.LocalDateTime;

public class HistoryFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private final Offer[] offers = new Offer[]{
            new Offer(Long.parseLong("41"),"Bilo", "Nekad", "foobar", LocalDateTime.now().minusDays(1).toString(), LocalDateTime.now().toString(), OfferStatus.COMPLETED,"Price 200","5t"),
            new Offer(Long.parseLong("47"),"Vise", "Nije", "foobar", LocalDateTime.now().plusDays(2).toString(), LocalDateTime.now().plusDays(2).plusHours(2).toString(), OfferStatus.CANCELED,"Price 200","5t")
    };

    public HistoryFragment() {
        super(R.layout.history_reservation_fragment);
    }

    @Override
    protected View initializeView(View view) {
        recyclerView = view.findViewById(R.id.history_res);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new HistoryReservationAdapter(offers, this::openOfferOverview);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

}
