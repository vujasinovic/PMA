package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.model.Offer;

import java.util.function.Consumer;

public class HistoryReservationAdapter extends RecyclerView.Adapter<HistoryReservationAdapter.HistoryReservationViewHolder> {

    final Offer[] data;
    final Consumer<Offer> onOfferSelected;
    private LayoutInflater layoutInflater;

    public HistoryReservationAdapter(Offer[] strings, Consumer<Offer> onOfferSelected) {
        data = strings;
        this.onOfferSelected = onOfferSelected;
    }

    @NonNull
    @Override
    public HistoryReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_res_list, parent, false);
        return new HistoryReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryReservationViewHolder holder, int position) {
        final Offer offer = data[position];
        final String title = offer.getLocationFrom() + " to " + offer.getLocationTo();

        holder.offerPlace.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class HistoryReservationViewHolder extends RecyclerView.ViewHolder {

        TextView offerPlace;
        TextView offerPrice;

        public HistoryReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            offerPlace = itemView.findViewById(R.id.historyPlace);
            offerPrice = itemView.findViewById(R.id.historyPrice);

            offerPlace.setOnClickListener(v -> {
                onOfferSelected.accept(data[getAdapterPosition()]);
            });
        }
    }
}
