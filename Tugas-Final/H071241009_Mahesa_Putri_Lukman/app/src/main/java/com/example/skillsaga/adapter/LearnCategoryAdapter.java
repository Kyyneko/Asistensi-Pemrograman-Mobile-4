package com.example.skillsaga.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.R;
import com.example.skillsaga.model.CategoryModel;

import java.util.List;

public class LearnCategoryAdapter extends RecyclerView.Adapter<LearnCategoryAdapter.ViewHolder> {
    private List<CategoryModel> categoryList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CategoryModel category);
    }

    public LearnCategoryAdapter(List<CategoryModel> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learn_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model = categoryList.get(position);
        holder.tvName.setText(model.getName());
        holder.ivIcon.setImageResource(model.getIconResId());

        try {
            holder.cvIconBg.setCardBackgroundColor(Color.parseColor(model.getColorHexBg()));

        } catch (IllegalArgumentException e) {
            holder.cvIconBg.setCardBackgroundColor(Color.parseColor("#9E9E9E"));
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(model));
    }

    @Override
    public int getItemCount() { return categoryList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvIconBg;
        ImageView ivIcon;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvIconBg = itemView.findViewById(R.id.cv_category_icon_bg);
            ivIcon = itemView.findViewById(R.id.iv_category_icon);
            tvName = itemView.findViewById(R.id.tv_category_name);
        }
    }
}