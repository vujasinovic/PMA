package com.example.transportivo.fragments;

import android.view.View;

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
            new Offer("Bilo", "Nekad", LocalDateTime.now().minusDays(1), LocalDateTime.now(), OfferStatus.OPEN),
            new Offer("Vise", "Nije", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), OfferStatus.OPEN)
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
        adapter = new HistoryReservationAdapter(offers);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
