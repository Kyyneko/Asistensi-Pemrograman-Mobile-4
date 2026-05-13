package com.example.usingsharpref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SwitchMaterial switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        SharedPreferences prefs = requireActivity().getSharedPreferences("LibraryPrefs", Context.MODE_PRIVATE);

        // Set kondisi switch berdasarkan data tersimpan
        boolean isDarkMode = prefs.getBoolean("isDarkMode", false);
        switchDarkMode.setChecked(isDarkMode);

        // Logika Ganti Tema
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("isDarkMode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Logika Logout
        btnLogout.setOnClickListener(v -> {
            // Hapus status login
            prefs.edit().putBoolean("isLoggedIn", false).apply();

            // Kembali ke LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}