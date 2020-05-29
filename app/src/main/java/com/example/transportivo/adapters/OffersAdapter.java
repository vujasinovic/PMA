package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.utils.FragmentHelper;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {
    private final String[] data;
    private final View.OnClickListener onClickListener;

    private LayoutInflater layoutInflater;

    public OffersAdapter(String[] strings, View.OnClickListener onClickListener) {
        data = strings;
        this.onClickListener = onClickListener;
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

        holder.offerPlace.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class OffersViewHolder extends RecyclerView.ViewHolder {


        TextView offerPlace;
        TextView offerPrice;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            offerPlace = itemView.findViewById(R.id.offerPlace);
            offerPrice = itemView.findViewById(R.id.offerPrice);

            offerPlace.setOnClickListener(onClickListener);
        }
    }
}
