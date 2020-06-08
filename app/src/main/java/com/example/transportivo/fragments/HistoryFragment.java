package com.example.transportivo.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.HistoryReservationAdapter;
import com.example.transportivo.model.Offer;
import com.example.transportivo.model.OfferStatus;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class HistoryFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
        adapter = new HistoryReservationAdapter(retrieve(), this::openOfferOverview);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void openOfferOverview(Offer offer) {
        final OfferOverviewFragmentArgs args = new OfferOverviewFragmentArgs.Builder(offer).build();
        Navigation.findNavController(getView()).navigate(R.id.nav_offer_overview, args.toBundle());
    }

    private Offer[] retrieve() {
        List<Offer> offerList = new ArrayList<>();

        final String URL = "content://com.example.transportivo.provider.OffersProvider/offers";

        Uri uri = Uri.parse(URL);
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, Offer.Fields.offerStatus + " IN  (?, ?) ",
                new String[]{OfferStatus.CANCELED.toString(), OfferStatus.COMPLETED.toString()}, null);

        if (cursor.moveToFirst()) {
            do {
                Offer offer = new Offer();
                offer.setDateTimeArrival(cursor.getString(cursor.getColumnIndex(Offer.Fields.dateTimeArrival)));
                offer.setDateTimeDeparture(cursor.getString(cursor.getColumnIndex(Offer.Fields.dateTimeDeparture)));
                offer.setLocationFrom(cursor.getString(cursor.getColumnIndex(Offer.Fields.locationFrom)));
                offer.setLocationTo(cursor.getString(cursor.getColumnIndex(Offer.Fields.locationTo)));

                String status = cursor.getString(cursor.getColumnIndex(Offer.Fields.offerStatus));
                if (nonNull(status)) {
                    offer.setOfferStatus(OfferStatus.valueOf(status));
                }

                offerList.add(offer);
            } while (cursor.moveToNext());
        }

        Offer[] offers = new Offer[offerList.size()];
        offerList.toArray(offers);

        return offers;
    }

}
