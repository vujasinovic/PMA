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

public class ActiveReservationAdapter extends RecyclerView.Adapter<ActiveReservationAdapter.ActiveReservationViewHolder> {

    private Offer[] data;
    private final Consumer<Offer> onOfferSelected;
    private LayoutInflater layoutInflater;

    public ActiveReservationAdapter(Offer[] offers, Consumer<Offer> onOfferSelected) {
        data = offers;
        this.onOfferSelected = onOfferSelected;
    }

    @NonNull
    @Override
    public ActiveReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.active_res_list, parent, false);
        return new ActiveReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveReservationViewHolder holder, int position) {
        Offer offer = data[position];
        final String title = offer.getLocationFrom() + " to " + offer.getLocationTo();
        holder.offerPlace.setText(title);
        holder.offerPrice.setText(offer.getPrice());
        holder.description.setText(offer.getDescription());
        holder.capacity.setText(offer.getCapacity());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    class ActiveReservationViewHolder extends RecyclerView.ViewHolder {
        TextView offerPlace;
        TextView offerPrice;
        TextView capacity;
        TextView description;

        ActiveReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            offerPlace = itemView.findViewById(R.id.offerActivePlace);
            offerPrice = itemView.findViewById(R.id.offerActivePrice);
            capacity = itemView.findViewById(R.id.offerActiveCapacity);
            description = itemView.findViewById(R.id.offerActiveDescription);

            offerPlace.setOnClickListener(v -> {
                onOfferSelected.accept(data[getAdapterPosition()]);
            });

        }
    }
}
