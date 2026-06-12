package com.example.skillsaga.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.R;
import com.example.skillsaga.adapter.LearnCategoryAdapter;
import com.example.skillsaga.model.CategoryModel;
import com.example.skillsaga.ui.activity.ModuleListActivity;

import java.util.ArrayList;
import java.util.List;

public class LearnFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCategories = view.findViewById(R.id.rv_learn_categories);

        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

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

        LearnCategoryAdapter adapter = new LearnCategoryAdapter(list, category -> {

            Intent intent = new Intent(getActivity(), ModuleListActivity.class);
            intent.putExtra("KATEGORI_TERPILIH", category.getName());
            startActivity(intent);
        });

        rvCategories.setAdapter(adapter);
    }
}