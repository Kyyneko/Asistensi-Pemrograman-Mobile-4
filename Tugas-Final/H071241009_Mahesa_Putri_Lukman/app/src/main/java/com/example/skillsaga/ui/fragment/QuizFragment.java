package com.example.skillsaga.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.R;
import com.example.skillsaga.model.CategoryModel;
import com.example.skillsaga.ui.activity.QuizLevelActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCategories = view.findViewById(R.id.rv_quiz_categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel("Science", R.drawable.icon_sains, "#E8F5E9", "#2E7D32"));
        list.add(new CategoryModel("Mathematics", R.drawable.icon_matematika, "#FFF3E0", "#E65100"));
        list.add(new CategoryModel("Computer", R.drawable.icon_komputer, "#E3F2FD", "#0263A1"));
        list.add(new CategoryModel("History", R.drawable.icon_sejarah, "#FCE4EC", "#C2185B"));
        list.add(new CategoryModel("Geography", R.drawable.icon_geografi, "#E0F2F1", "#00796B"));
        list.add(new CategoryModel("Sports", R.drawable.icon_olahraga, "#FFF8E1", "#FBC02D"));
        list.add(new CategoryModel("Arts", R.drawable.icon_seni, "#F3E5F5", "#7B1FA2"));
        list.add(new CategoryModel("Animals", R.drawable.icon_hewan, "#EFEBE9", "#5D4037"));
        list.add(new CategoryModel("Vehicles", R.drawable.icon_kendaraan, "#E8EAF6", "#303F9F"));
        list.add(new CategoryModel("Mythology", R.drawable.icon_mitologi, "#E8EAF6", "#303F9F"));
        list.add(new CategoryModel("Literature", R.drawable.icon_sastra, "#F3E5F5", "#880E4F"));
        list.add(new CategoryModel("General", R.drawable.icon_umum, "#ECEFF1", "#455A64"));

        QuizCategoryAdapter adapter = new QuizCategoryAdapter(list, category -> {
            Intent intent = new Intent(getActivity(), QuizLevelActivity.class);
            intent.putExtra("KATEGORI_KUIS", category.getName());
            startActivity(intent);
        });

        rvCategories.setAdapter(adapter);
    }

    public static class QuizCategoryAdapter extends RecyclerView.Adapter<QuizCategoryAdapter.ViewHolder> {
        private List<CategoryModel> categoryList;
        private OnItemClickListener listener;

        public interface OnItemClickListener { void onItemClick(CategoryModel category); }

        public QuizCategoryAdapter(List<CategoryModel> categoryList, OnItemClickListener listener) {
            this.categoryList = categoryList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_category, parent, false);
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
                cvIconBg = itemView.findViewById(R.id.cv_quiz_icon_bg);
                ivIcon = itemView.findViewById(R.id.iv_quiz_category_icon);
                tvName = itemView.findViewById(R.id.tv_quiz_category_name);
            }
        }
    }
}