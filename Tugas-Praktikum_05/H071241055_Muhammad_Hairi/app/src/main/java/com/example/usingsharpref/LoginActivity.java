package com.example.usingsharpref; // Sesuaikan dengan nama package Anda!

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. CEK TEMA SEBELUM LAYAR DIBUAT (Agar layar tidak berkedip)
        SharedPreferences prefs = getSharedPreferences("LibraryPrefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("isDarkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // 2. CEK STATUS LOGIN (Sesi)
        // Kalau sudah login sebelumnya, langsung lewati halaman ini dan buka MainActivity
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Tutup LoginActivity agar tidak bisa di-back
            return;
        }

        // Kalau belum login, tampilkan layout login
        setContentView(R.layout.activity_login);

        // Hubungkan variabel dengan ID komponen di XML
        EditText etUsername = findViewById(R.id.et_login_username);
        EditText etPassword = findViewById(R.id.et_login_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnToRegister = findViewById(R.id.btn_to_register);

        // 3. LOGIKA TOMBOL LOGIN
        btnLogin.setOnClickListener(v -> {
            String inputUser = etUsername.getText().toString().trim();
            String inputPass = etPassword.getText().toString().trim();

            // Cegah inputan kosong
            if (inputUser.isEmpty() || inputPass.isEmpty()) {
                Toast.makeText(this, "Username dan Password wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ambil data akun yang pernah didaftarkan dari SharedPreferences
            // Jika belum ada yang daftar, nilai default-nya adalah string kosong ""
            String savedUser = prefs.getString("saved_username", "");
            String savedPass = prefs.getString("saved_password", "");

            // Cocokkan inputan dengan data yang tersimpan
            if (inputUser.equals(savedUser) && inputPass.equals(savedPass)) {
                // Jika cocok, ubah status sesi login menjadi TRUE
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.putString("username", inputUser); // Simpan nama untuk ditampilkan di menu Settings
                editor.apply();

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show();

                // Lempar ke halaman utama
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                // Jika tidak cocok atau belum pernah register
                Toast.makeText(this, "Username atau Password salah! (Atau akun belum didaftarkan)", Toast.LENGTH_LONG).show();
            }
        });

        // 4. LOGIKA TOMBOL REGISTER
        btnToRegister.setOnClickListener(v -> {
            // Pindah ke halaman RegisterActivity
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}