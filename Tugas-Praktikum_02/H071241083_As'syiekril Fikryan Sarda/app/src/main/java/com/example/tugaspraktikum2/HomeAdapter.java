package com.example.tugaspraktikum2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<Post> listPost;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public HomeAdapter(ArrayList<Post> list) {
        this.listPost = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listPost.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.ivProfile.setImageResource(post.getUserProfileImage());
        holder.ivPost.setImageResource(post.getPostImage());
        holder.tvCaption.setText(post.getCaption());

        View.OnClickListener goToProfile = v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onProfileClicked(post);
            }
        };

        holder.ivProfile.setOnClickListener(goToProfile);
        holder.tvUsername.setOnClickListener(goToProfile);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile, ivPost;
        TextView tvUsername, tvCaption;
        LinearLayout layoutHeader;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile_home);
            tvUsername = itemView.findViewById(R.id.tv_username_home);
            ivPost = itemView.findViewById(R.id.iv_post_home);
            tvCaption = itemView.findViewById(R.id.tv_caption_home);
            layoutHeader = itemView.findViewById(R.id.layout_header);
        }
    }

    public interface OnItemClickCallback {
        void onProfileClicked(Post data);
    }
}
