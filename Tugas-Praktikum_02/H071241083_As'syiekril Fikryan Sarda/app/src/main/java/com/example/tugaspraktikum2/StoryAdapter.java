package com.example.tugaspraktikum2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    private ArrayList<Story> listStory;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public StoryAdapter(ArrayList<Story> list) {
        this.listStory = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = listStory.get(position);
        holder.ivStory.setImageResource(story.getImage());
        holder.tvStoryTitle.setText(story.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickCallback != null) {
                onItemClickCallback.onItemClicked(story);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStory;
        TextView tvStoryTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStory = itemView.findViewById(R.id.iv_story);
            tvStoryTitle = itemView.findViewById(R.id.tv_story_title);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Story data);
    }
}
