package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;

public class ActiveReservationAdapter extends RecyclerView.Adapter<ActiveReservationAdapter.ActiveReservationViewHolder> {

    String[] data = {};

    private LayoutInflater layoutInflater;

    public ActiveReservationAdapter(String[] strings) {
        data = strings;

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
        String title = data[position];

        holder.offerPlace.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class ActiveReservationViewHolder extends RecyclerView.ViewHolder {

        TextView offerPlace;
        TextView offerPrice;

        public ActiveReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            offerPlace = itemView.findViewById(R.id.offerActivePlace);
            offerPrice = itemView.findViewById(R.id.offerActivePrice);
        }
    }
}
