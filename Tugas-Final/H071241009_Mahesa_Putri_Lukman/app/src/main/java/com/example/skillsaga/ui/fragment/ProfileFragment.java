package com.example.skillsaga.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.skillsaga.R;
import com.example.skillsaga.ui.activity.EditProfileActivity;
import com.example.skillsaga.ui.activity.LoginActivity;
import com.example.skillsaga.ui.activity.MainActivity;
import com.google.android.material.button.MaterialButton;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvId;
    private ImageView ivProfileAvatar;
    private SharedPreferences prefs;

    private final int[] avatarImages = {
            R.drawable.role_tank, R.drawable.role_fighter, R.drawable.role_assassin,
            R.drawable.role_mage, R.drawable.role_marksman, R.drawable.role_healer
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfileAvatar = view.findViewById(R.id.iv_profile_avatar);
        tvName = view.findViewById(R.id.tv_profile_name);
        tvId = view.findViewById(R.id.tv_profile_id);
        Switch switchTheme = view.findViewById(R.id.switch_theme);
        MaterialButton btnLogout = view.findViewById(R.id.btn_logout);
        CardView cvEditProfile = view.findViewById(R.id.cv_edit_profile);
        LinearLayout llNotification = view.findViewById(R.id.ll_menu_notification);

        prefs = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        cvEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        switchTheme.setChecked(isDarkMode);

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        llNotification.setOnClickListener(v -> {
            prefs.edit().putBoolean("is_notification_enabled", true).apply();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
                    return;
                }
            }
            kirimNotifikasiSakti();
        });

        btnLogout.setOnClickListener(v -> {
            prefs.edit().putBoolean("isLoggedIn", false).apply();
            Toast.makeText(getActivity(), "Manage to Logout. See You!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (prefs != null && tvName != null && ivProfileAvatar != null) {

            String savedName = prefs.getString("registered_name", "Pelajar Tangguh");
            String savedEmail = prefs.getString("registered_email", "H071241009");
            tvName.setText(savedName);
            tvId.setText("ID: " + savedEmail);

            String savedPhotoUri = prefs.getString("profile_photo_real_uri", null);
            int avatarIndex = prefs.getInt("user_avatar_index", 3);

            if (savedPhotoUri != null) {
                ivProfileAvatar.setImageURI(Uri.parse(savedPhotoUri));
            } else {
                ivProfileAvatar.setImageResource(avatarImages[avatarIndex]);
            }
        }
    }

    private void kirimNotifikasiSakti() {
        String channelId = "SKILLSAGA_CHANNEL";
        NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "SkillSaga Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel untuk pengingat belajar harian");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent intent = new Intent(requireActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Study Time! 🚀")
                .setContentText("Your XP is waiting! Complete a new quiz module today and level up your skills.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(1001, builder.build());
            Toast.makeText(getContext(), "Test notification sent!", Toast.LENGTH_SHORT).show();
        }
    }
}