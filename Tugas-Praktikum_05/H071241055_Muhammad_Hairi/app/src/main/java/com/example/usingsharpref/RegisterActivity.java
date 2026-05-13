package com.example.usingsharpref; // Gunakan NIM Anda

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etRegUser = findViewById(R.id.et_reg_username);
        EditText etRegPass = findViewById(R.id.et_reg_password);
        Button btnRegister = findViewById(R.id.btn_register_action);

        btnRegister.setOnClickListener(v -> {
            String user = etRegUser.getText().toString();
            String pass = etRegPass.getText().toString();

            if (!user.isEmpty() && !pass.isEmpty()) {
                // Simpan data pendaftaran ke SharedPreferences
                SharedPreferences prefs = getSharedPreferences("LibraryPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("saved_username", user);
                editor.putString("saved_password", pass);
                editor.apply();

                Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke halaman Login
            } else {
                Toast.makeText(this, "Lengkapi data terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}