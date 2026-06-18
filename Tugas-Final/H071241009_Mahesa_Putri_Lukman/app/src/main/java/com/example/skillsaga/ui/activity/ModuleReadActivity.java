package com.example.skillsaga.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsaga.R;
import com.google.android.material.button.MaterialButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ModuleReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_read);

        ImageView ivBack = findViewById(R.id.iv_back_from_read);
        TextView tvLabel = findViewById(R.id.tv_read_category_label);
        TextView tvTitle = findViewById(R.id.tv_read_title);
        TextView tvContent = findViewById(R.id.tv_read_content);
        MaterialButton btnStartQuiz = findViewById(R.id.btn_start_quiz_from_module);

        String judulMateri = getIntent().getStringExtra("JUDUL_MATERI");
        String kategoriAsal = getIntent().getStringExtra("KATEGORI_ASAL");

        if (judulMateri != null) tvTitle.setText(judulMateri);
        if (kategoriAsal != null) tvLabel.setText("Module: " + kategoriAsal);

        if (judulMateri != null) {
            String isiTeks = bacaKontenDariJSON(judulMateri);
            tvContent.setText(isiTeks);
        }

        ivBack.setOnClickListener(v -> finish());

        btnStartQuiz.setOnClickListener(v -> {

            if (judulMateri != null) {
                android.content.SharedPreferences prefs = getSharedPreferences("ModuleProgress", android.content.Context.MODE_PRIVATE);
                java.util.Set<String> readModules = prefs.getStringSet("read_modules", new java.util.HashSet<>());

                java.util.Set<String> newReadModules = new java.util.HashSet<>(readModules);
                newReadModules.add(judulMateri);

                prefs.edit().putStringSet("read_modules", newReadModules).apply();
            }

            Intent intent = new Intent(ModuleReadActivity.this, QuizLevelActivity.class);
            intent.putExtra("KATEGORI_KUIS", kategoriAsal);
            startActivity(intent);
            finish();
        });
    }

    private String bacaKontenDariJSON(String judulTarget) {
        String jsonStr = null;
        try {
            InputStream is = getAssets().open("modules.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonStr = new String(buffer, StandardCharsets.UTF_8);

            JSONObject obj = new JSONObject(jsonStr);
            JSONArray arrayModul = obj.getJSONArray("modul");

            for (int i = 0; i < arrayModul.length(); i++) {
                JSONObject modulItem = arrayModul.getJSONObject(i);
                String judulDiJSON = modulItem.getString("judul");

                if (judulDiJSON.equals(judulTarget)) {
                    return modulItem.getString("konten");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Oops! Content for '" + judulTarget + "' is currently being written by our academic team. Please stay tuned for our next update!";
    }
}