package com.example.pratikum2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pratikum2.R;
import com.example.pratikum2.StoryDetailActivity;
import com.example.pratikum2.models.Story;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private Context context;
    private List<Story> storyList;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = storyList.get(position);
        holder.tvStoryTitle.setText(story.getTitle());
        holder.imgStory.setImageResource(story.getStoryImageResId());

        // Navigasi ke StoryDetailActivity saat diklik
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StoryDetailActivity.class);
            intent.putExtra("STORY_IMAGE", story.getStoryImageResId());
            intent.putExtra("STORY_TITLE", story.getTitle());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStory;
        TextView tvStoryTitle;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStory = itemView.findViewById(R.id.img_item_story);
            tvStoryTitle = itemView.findViewById(R.id.tv_item_story_title);
        }
    }
}