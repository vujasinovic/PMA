package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    String[] data = {};

    private LayoutInflater layoutInflater;

    public NotificationAdapter(String[] strings) {
        data = strings;

    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String title = data[position];

        holder.notificationTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView notificationTitle;
        TextView notificationContext;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageIcon);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationContext = itemView.findViewById(R.id.notificationContext);
        }
    }
}
