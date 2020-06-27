package com.example.transportivo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.model.Comment;

import lombok.SneakyThrows;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    Comment[] data = {};

    private LayoutInflater layoutInflater;

    public CommentAdapter(Comment[] strings) {
        data = strings;

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comments_list_item, parent, false);
        return new CommentAdapter.CommentViewHolder(view);
    }

    @SneakyThrows
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        final Comment comment = data[position];
        holder.userName.setText(comment.getCommentatorName());
        holder.comment.setText(comment.getComment());
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
