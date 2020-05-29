package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;

public class HistoryReservationAdapter extends RecyclerView.Adapter<HistoryReservationAdapter.HistoryReservationViewHolder> {

    String[] data = {};

    private LayoutInflater layoutInflater;

    public HistoryReservationAdapter(String[] strings) {
        data = strings;

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
        String title = data[position];

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
        }
    }
}
