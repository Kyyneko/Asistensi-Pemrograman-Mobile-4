package com.example.skillsaga.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillsaga.database.DatabaseHelper;
import com.example.skillsaga.R;

import java.util.ArrayList;
import java.util.List;

public class QuizLevelActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView rvLevels;
    private String kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_level);

        ImageView ivBack = findViewById(R.id.iv_back_to_quiz_catalog);
        TextView tvTitle = findViewById(R.id.tv_level_category_title);
        rvLevels = findViewById(R.id.rv_level_grid);

        dbHelper = new DatabaseHelper(this);

        kategori = getIntent().getStringExtra("KATEGORI_KUIS");
        if (kategori == null) kategori = "Arena";

        tvTitle.setText("Arena: " + kategori);
        ivBack.setOnClickListener(v -> finish());

        rvLevels.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    protected void onResume() {
        super.onResume();
        muatPetaLevel();
    }

    private void muatPetaLevel() {
        int unlockedLevel = dbHelper.getUnlockedLevel(kategori);

        List<LevelModel> levelList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            boolean isUnlocked = (i <= unlockedLevel);
            levelList.add(new LevelModel(i, isUnlocked));
        }

        String finalKategori = kategori;
        LevelAdapter adapter = new LevelAdapter(levelList, levelModel -> {
            if (levelModel.isUnlocked) {
                Intent intent = new Intent(QuizLevelActivity.this, QuizActivity.class);
                intent.putExtra("KATEGORI_KUIS", finalKategori);
                intent.putExtra("LEVEL_KUIS", levelModel.levelNumber);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Terkunci! Selesaikan Level " + (levelModel.levelNumber - 1) + " dulu dengan nilai min 80.", Toast.LENGTH_SHORT).show();
            }
        });

        rvLevels.setAdapter(adapter);
    }

    public static class LevelModel {
        int levelNumber;
        boolean isUnlocked;
        public LevelModel(int levelNumber, boolean isUnlocked) {
            this.levelNumber = levelNumber;
            this.isUnlocked = isUnlocked;
        }
    }

    public static class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
        List<LevelModel> list;
        OnItemClickListener listener;

        public interface OnItemClickListener { void onItemClick(LevelModel level); }

        public LevelAdapter(List<LevelModel> list, OnItemClickListener listener) {
            this.list = list;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_level, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LevelModel model = list.get(position);
            holder.tvLevel.setText(String.valueOf(model.levelNumber));

            if (model.isUnlocked) {
                holder.cvContainer.setCardBackgroundColor(Color.WHITE);
                holder.tvLevel.setTextColor(Color.parseColor("#37474F"));
                holder.ivIcon.setImageResource(android.R.drawable.star_on);
                holder.ivIcon.setColorFilter(Color.parseColor("#FFA000"));
            } else {
                holder.cvContainer.setCardBackgroundColor(Color.parseColor("#E2E8F0"));
                holder.tvLevel.setTextColor(Color.parseColor("#90A4AE"));
                holder.ivIcon.setImageResource(android.R.drawable.ic_secure);
                holder.ivIcon.setColorFilter(Color.parseColor("#90A4AE"));
            }

            holder.itemView.setOnClickListener(v -> listener.onItemClick(model));
        }

        @Override
        public int getItemCount() { return list.size(); }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            CardView cvContainer;
            TextView tvLevel;
            ImageView ivIcon;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cvContainer = itemView.findViewById(R.id.cv_level_container);
                tvLevel = itemView.findViewById(R.id.tv_level_number);
                ivIcon = itemView.findViewById(R.id.iv_level_status_icon);
            }
        }
    }
}