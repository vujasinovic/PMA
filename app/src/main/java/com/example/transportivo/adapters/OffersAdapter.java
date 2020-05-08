package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {
    String[] data = {};

    private LayoutInflater layoutInflater;

    public OffersAdapter(String[] strings) {
        data = strings;

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
        String title = data[position];

        holder.offerDescription.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class OffersViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView offerDescription;
        TextView offerPrice;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageIcon);
            offerDescription = itemView.findViewById(R.id.offerDescription);
            offerPrice = itemView.findViewById(R.id.offerPrice);
        }
    }
}
