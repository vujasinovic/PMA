package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.model.Notification;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    String[] data = {};

    private LayoutInflater layoutInflater;

    public CommentAdapter(String[] strings) {
        data = strings;

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comments_list_item, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final String username = data[position];

        holder.userName.setText(username);
        holder.comment.setText("Dobar prevoz krompira");
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.nameAndLastname);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
