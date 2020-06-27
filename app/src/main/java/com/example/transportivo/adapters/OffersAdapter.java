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

        holder.place.setText(title);
        holder.price.setText(offer.getPrice());
        holder.capacity.setText(offer.getCapacity());
        holder.description.setText(offer.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class OffersViewHolder extends RecyclerView.ViewHolder {
        final TextView place;
        final TextView price;
        final TextView capacity;
        final TextView description;

        OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.offerPlace);
            price = itemView.findViewById(R.id.offerPrice);
            capacity = itemView.findViewById(R.id.offerCapacity);
            description = itemView.findViewById(R.id.offerDescription);

            place.setOnClickListener(v -> {
                onOfferSelected.accept(data[getAdapterPosition()]);
            });
        }
    }
}
