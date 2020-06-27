package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Notification[] data = {};

    private LayoutInflater layoutInflater;

    public NotificationAdapter(Notification[] strings) {
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
        final Notification notification = data[position];

        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationContext.setText(notification.getBody());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView notificationTitle;
        TextView notificationContext;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationContext = itemView.findViewById(R.id.notificationContext);
        }
    }
}
