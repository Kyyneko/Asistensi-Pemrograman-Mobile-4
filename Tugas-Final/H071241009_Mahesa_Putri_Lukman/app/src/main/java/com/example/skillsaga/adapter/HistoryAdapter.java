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

import com.example.skillsaga.model.HistoryModel;
import com.example.skillsaga.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryModel> historyList;

    public HistoryAdapter(List<HistoryModel> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel model = historyList.get(position);

        holder.tvTitle.setText(model.getCategory() + " Quiz");
        holder.tvDateLevel.setText(model.getDate() + " • " + model.getLevel());
        holder.tvScore.setText(model.getScore() + "/100");
        holder.tvXp.setText("+" + model.getXp() + " XP");

        holder.ivIcon.setImageResource(getIconBerdasarkanKategori(model.getCategory()));
        holder.cvIconBg.setCardBackgroundColor(Color.parseColor(getBgColorBerdasarkanKategori(model.getCategory())));
    }

    @Override
    public int getItemCount() { return historyList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvIconBg;
        ImageView ivIcon;
        TextView tvTitle, tvDateLevel, tvScore, tvXp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvIconBg = itemView.findViewById(R.id.cv_icon_bg);
            ivIcon = itemView.findViewById(R.id.iv_history_icon);
            tvTitle = itemView.findViewById(R.id.tv_history_title);
            tvDateLevel = itemView.findViewById(R.id.tv_history_date_level);
            tvScore = itemView.findViewById(R.id.tv_history_score);
            tvXp = itemView.findViewById(R.id.tv_history_xp);
        }
    }

    private int getIconBerdasarkanKategori(String kategori) {
        if (kategori == null) return R.drawable.icon_umum;

        switch (kategori.toLowerCase()) {
            case "science": return R.drawable.icon_sains;
            case "mathematics": return R.drawable.icon_matematika;
            case "computer": return R.drawable.icon_komputer;
            case "history": return R.drawable.icon_sejarah;
            case "geography": return R.drawable.icon_geografi;
            case "sports": return R.drawable.icon_olahraga;
            case "arts": return R.drawable.icon_seni;
            case "animals": return R.drawable.icon_hewan;
            case "vehicles": return R.drawable.icon_kendaraan;
            case "mythology": return R.drawable.icon_mitologi;
            case "literature": return R.drawable.icon_sastra;
            case "general":
            default:
                return R.drawable.icon_umum;
        }
    }

    private String getBgColorBerdasarkanKategori(String kategori) {
        if (kategori == null) return "#ECEFF1";

        switch (kategori.toLowerCase()) {
            case "science": return "#E8F5E9";
            case "mathematics": return "#FFF3E0";
            case "computer": return "#E3F2FD";
            case "history": return "#FCE4EC";
            case "geography": return "#E0F2F1";
            case "sports": return "#FFF8E1";
            case "arts": return "#F3E5F5";
            case "animals": return "#EFEBE9";
            case "vehicles": return "#E8EAF6";
            case "mythology": return "#E8EAF6";
            case "literature": return "#F3E5F5";
            case "general":
            default:
                return "#ECEFF1";
        }
    }
}