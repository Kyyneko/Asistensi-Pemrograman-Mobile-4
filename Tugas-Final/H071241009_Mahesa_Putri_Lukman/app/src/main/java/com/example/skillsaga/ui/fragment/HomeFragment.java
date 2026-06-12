package com.example.skillsaga.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skillsaga.R;
import com.example.skillsaga.database.DatabaseHelper;

public class HomeFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private TextView tvTotalQuiz, tvTotalXp, tvWelcome, tvTotalModule, tvFavoriteCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getContext());

        tvWelcome = view.findViewById(R.id.tv_welcome_name);
        tvTotalQuiz = view.findViewById(R.id.tv_home_total_quiz);
        tvTotalXp = view.findViewById(R.id.tv_home_total_xp);
        tvTotalModule = view.findViewById(R.id.tv_home_total_module);
        tvFavoriteCategory = view.findViewById(R.id.tv_home_favorite_category);
        ImageView ivMainAvatar = view.findViewById(R.id.iv_main_avatar);

        SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int avatarIndex = prefs.getInt("user_avatar_index", 3);
        int[] avatarImages = {
                R.drawable.role_tank, R.drawable.role_fighter, R.drawable.role_assassin,
                R.drawable.role_mage, R.drawable.role_marksman, R.drawable.role_healer
        };
        if (ivMainAvatar != null) {
            ivMainAvatar.setImageResource(avatarImages[avatarIndex]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        muatStatistikDashboard();
    }

    private void muatStatistikDashboard() {
        if (dbHelper != null && tvTotalQuiz != null && tvTotalXp != null) {
            int totalKuisSelesai = dbHelper.getTotalQuizCompleted();
            int totalXpTerkumpul = dbHelper.getTotalXP();

            tvTotalQuiz.setText(String.valueOf(totalKuisSelesai));
            tvTotalXp.setText(String.valueOf(totalXpTerkumpul));
            if (tvTotalModule != null) {
                SharedPreferences modulePrefs = getActivity().getSharedPreferences("ModuleProgress", Context.MODE_PRIVATE);
                java.util.Set<String> readModules = modulePrefs.getStringSet("read_modules", new java.util.HashSet<>());
                tvTotalModule.setText(String.valueOf(readModules.size()));
            }

            int playerLevel = (totalXpTerkumpul / 1000) + 1;
            String gelar = "Novice Explorer";
            String trofi = "🌱";

            if (playerLevel >= 10) {
                gelar = "Legendary Scholar";
                trofi = "🏆👑";
            } else if (playerLevel >= 7) {
                gelar = "Master of Logic";
                trofi = "🏅";
            } else if (playerLevel >= 4) {
                gelar = "Advanced Learner";
                trofi = "⭐";
            }

            SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            String namaUser = prefs.getString("registered_name", "Explorer");

            if (tvWelcome != null) {
                String pesanSambutan = "Lv." + playerLevel + " : " + gelar + " " + trofi +
                        "\nHello, " + namaUser + "! Ready to set a new record today?";
                tvWelcome.setText(pesanSambutan);
            }

            if (tvFavoriteCategory != null) {
                String favCat = dbHelper.getKategoriFavorit();
                if (favCat == null || favCat.isEmpty()) {
                    tvFavoriteCategory.setText("None yet");
                } else {
                    tvFavoriteCategory.setText(favCat);
                }
            }
        }
    }
}