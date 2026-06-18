package com.example.tugaspraktikum2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {
    private ArrayList<Post> listPost;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ProfileFeedAdapter(ArrayList<Post> list) {
        this.listPost = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listPost.get(position);

        if (post.getImageUri() != null) {
            holder.ivPostGrid.setImageURI(android.net.Uri.parse(post.getImageUri()));
        } else {
            holder.ivPostGrid.setImageResource(post.getPostImage());
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onItemClicked(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPostGrid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPostGrid = itemView.findViewById(R.id.iv_post_grid);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Post data);
    }
}