package com.example.pratikum2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pratikum2.PostDetailActivity;
import com.example.pratikum2.ProfileActivity;
import com.example.pratikum2.R;
import com.example.pratikum2.models.Post;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private Context context;
    private List<Post> postList;
    private boolean isProfileView;

    public FeedAdapter(Context context, List<Post> postList) {
        this(context, postList, false);
    }

    public FeedAdapter(Context context, List<Post> postList, boolean isProfileView) {
        this.context = context;
        this.postList = postList;
        this.isProfileView = isProfileView;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.tvUsername.setText(post.getUsername());
        holder.tvCaption.setText(post.getCaption());
        
        // Handle Profile Image
        if (post.getUserProfileResId() != -1) {
            holder.imgProfile.setImageResource(post.getUserProfileResId());
        } else if (post.getUserProfileUri() != null) {
            holder.imgProfile.setImageURI(Uri.parse(post.getUserProfileUri()));
        }

        // Handle Post Image
        if (post.getImageResId() != -1) {
            holder.imgPost.setImageResource(post.getImageResId());
        } else if (post.getImageUri() != null) {
            holder.imgPost.setImageURI(Uri.parse(post.getImageUri()));
        }

        if (isProfileView) {
            holder.layoutProfileHeader.setVisibility(View.GONE);
            holder.tvCaption.setVisibility(View.GONE);
            
            ViewGroup.LayoutParams params = holder.imgPost.getLayoutParams();
            params.height = (int) (150 * context.getResources().getDisplayMetrics().density);
            holder.imgPost.setLayoutParams(params);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("POST_DATA", post);
                context.startActivity(intent);
            });
        } else {
            holder.layoutProfileHeader.setVisibility(View.VISIBLE);
            holder.tvCaption.setVisibility(View.VISIBLE);
            
            View.OnClickListener goToProfile = v -> {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("USERNAME", post.getUsername());
                intent.putExtra("PROFILE_IMAGE_RES", post.getUserProfileResId());
                intent.putExtra("PROFILE_IMAGE_URI", post.getUserProfileUri());
                context.startActivity(intent);
            };
            holder.imgProfile.setOnClickListener(goToProfile);
            holder.tvUsername.setOnClickListener(goToProfile);
        }
    }

    @Override
    public int getItemCount() { return postList.size(); }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile, imgPost;
        TextView tvUsername, tvCaption;
        View layoutProfileHeader;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_item_profile);
            imgPost = itemView.findViewById(R.id.img_item_post);
            tvUsername = itemView.findViewById(R.id.tv_item_username);
            tvCaption = itemView.findViewById(R.id.tv_item_caption);
            layoutProfileHeader = itemView.findViewById(R.id.layout_item_header);
        }
    }
}