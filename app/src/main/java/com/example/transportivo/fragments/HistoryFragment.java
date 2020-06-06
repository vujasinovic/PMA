package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.HistoryReservationAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View view;

    private final Offer[] offers = new Offer[]{
            new Offer("Bilo", "Nekad", LocalDateTime.now().minusDays(1), LocalDateTime.now(), OfferStatus.OPEN),
            new Offer("Vise", "Nije", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(2), OfferStatus.OPEN)
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View response = nonNull(view) ? view : createView(inflater, container);
        return response;
    }

    private View createView(LayoutInflater inflater, ViewGroup container) {
        final View view = inflater.inflate(R.layout.history_reservation_fragment, container, false);
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
