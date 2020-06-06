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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {
    private final Offer[] data;
    private final Consumer<Offer> onOfferSelected;

    private LayoutInflater layoutInflater;

    public OffersAdapter(Offer[] data, Consumer<Offer> onOfferSelected) {
        this.data = data;
        this.onOfferSelected = onOfferSelected;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.offers_res_list_item, parent, false);
        return new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        final Offer offer = data[position];
        final String title = offer.getLocationFrom() + " to " + offer.getLocationTo();

        holder.offerPlace.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder {
        final TextView offerPlace;
        final TextView offerPrice;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            offerPlace = itemView.findViewById(R.id.offerPlace);
            offerPrice = itemView.findViewById(R.id.offerPrice);

            offerPlace.setOnClickListener(v -> {
                onOfferSelected.accept(data[getAdapterPosition()]);
            });
        }
    }
}
