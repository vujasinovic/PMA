package com.example.transportivo.fragments;

import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.NotificationAdapter;

public class NotificationFragment extends BaseFragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] strings = {"Reservation", "Comment", "Rating"};

    public NotificationFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    protected View initializeView(View view) {

        final RecyclerView recyclerView = view.findViewById(R.id.notifications);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new NotificationAdapter(strings);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
