package com.example.skillsaga.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsaga.network.ApiClient;
import com.example.skillsaga.database.DatabaseHelper;
import com.example.skillsaga.R;
import com.example.skillsaga.network.TriviaApiService;
import com.example.skillsaga.model.TriviaResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {

    private TextView tvCategory, tvCounter, tvScore, tvQuestion;
    private LinearProgressIndicator pbTimer;
    private MaterialButton btnA, btnB, btnC, btnD;
    private ImageView ivClose;

    private String kategoriKuis = "General";
    private int levelKuis = 1;
    private List<SoalModel> daftarSoal = new ArrayList<>();
    private int soalSekarang = 0;
    private int skorSementara = 0;
    private CountDownTimer timer;
    private final long WAKTU_PER_SOAL = 15000;
    private boolean sudahMenjawab = false;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        dbHelper = new DatabaseHelper(this);

        ivClose = findViewById(R.id.iv_close_quiz);
        tvCategory = findViewById(R.id.tv_quiz_category);
        tvCounter = findViewById(R.id.tv_question_counter);
        tvScore = findViewById(R.id.tv_current_score);
        pbTimer = findViewById(R.id.pb_timer);
        tvQuestion = findViewById(R.id.tv_question_text);

        btnA = findViewById(R.id.btn_option_a);
        btnB = findViewById(R.id.btn_option_b);
        btnC = findViewById(R.id.btn_option_c);
        btnD = findViewById(R.id.btn_option_d);

        if (getIntent().hasExtra("KATEGORI_KUIS")) {
            kategoriKuis = getIntent().getStringExtra("KATEGORI_KUIS");
        }
        levelKuis = getIntent().getIntExtra("LEVEL_KUIS", 1);

        tvCategory.setText(kategoriKuis + " - Level " + levelKuis);

        ivClose.setOnClickListener(v -> showExitDialog());
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() { showExitDialog(); }
        });

        btnA.setOnClickListener(v -> cekJawaban(btnA));
        btnB.setOnClickListener(v -> cekJawaban(btnB));
        btnC.setOnClickListener(v -> cekJawaban(btnC));
        btnD.setOnClickListener(v -> cekJawaban(btnD));

        tvQuestion.setText("Connecting to the Open Trivia DB satellite...");
        nonaktifkanTombol(true);

        muatSoalDariAPI();
    }

    private void muatSoalDariAPI() {
        SharedPreferences quizCache = getSharedPreferences("QuizCache", Context.MODE_PRIVATE);
        String cacheKey = "SOAL_" + kategoriKuis + "_LEVEL_" + levelKuis;
        String savedJson = quizCache.getString(cacheKey, null);

        if (savedJson != null) {
            Log.d("QUIZ_ENGINE", "Loading frozen questions from local storage...");
            TriviaResponse responseDariCache = new Gson().fromJson(savedJson, TriviaResponse.class);
            prosesDataSoal(responseDariCache);
        } else {
            Log.d("QUIZ_ENGINE", "Fetching new questions from the internet...");
            TriviaApiService apiService = ApiClient.getClient().create(TriviaApiService.class);

            int apiCategoryId = 9;
            String cat = kategoriKuis.toLowerCase();
            if (cat.contains("science")) apiCategoryId = 17;
            else if (cat.contains("computer")) apiCategoryId = 18;
            else if (cat.contains("mathematics")) apiCategoryId = 19;
            else if (cat.contains("history")) apiCategoryId = 23;
            else if (cat.contains("geography")) apiCategoryId = 22;
            else if (cat.contains("arts")) apiCategoryId = 25;
            else if (cat.contains("sports")) apiCategoryId = 21;
            else if (cat.contains("animals")) apiCategoryId = 27;
            else if (cat.contains("literature")) apiCategoryId = 10;
            else if (cat.contains("mythology")) apiCategoryId = 20;
            else if (cat.contains("vehicles")) apiCategoryId = 28;

            Call<TriviaResponse> call = apiService.getQuestions(5, apiCategoryId, "multiple");

            call.enqueue(new Callback<TriviaResponse>() {
                @Override
                public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getResults().size() > 0) {
                        String jsonUntukDisimpan = new Gson().toJson(response.body());
                        quizCache.edit().putString(cacheKey, jsonUntukDisimpan).apply();
                        prosesDataSoal(response.body());
                    } else {
                        muatSoalDummy();
                        nonaktifkanTombol(false);
                        tampilkanSoal();
                    }
                }

                @Override
                public void onFailure(Call<TriviaResponse> call, Throwable t) {
                    Toast.makeText(QuizActivity.this, "Failed to connect. Using offline questions.", Toast.LENGTH_SHORT).show();
                    muatSoalDummy();
                    nonaktifkanTombol(false);
                    tampilkanSoal();
                }
            });
        }
    }

    private void prosesDataSoal(TriviaResponse responseBody) {
        daftarSoal.clear();
        for (TriviaResponse.TriviaQuestion q : responseBody.getResults()) {
            String soalBersih = Html.fromHtml(q.getQuestion(), Html.FROM_HTML_MODE_LEGACY).toString();
            String jawabanBenar = Html.fromHtml(q.getCorrectAnswer(), Html.FROM_HTML_MODE_LEGACY).toString();

            List<String> semuaPilihan = new ArrayList<>();
            semuaPilihan.add(jawabanBenar);
            for (String salah : q.getIncorrectAnswers()) {
                semuaPilihan.add(Html.fromHtml(salah, Html.FROM_HTML_MODE_LEGACY).toString());
            }
            Collections.shuffle(semuaPilihan);

            daftarSoal.add(new SoalModel(
                    soalBersih,
                    semuaPilihan.get(0), semuaPilihan.get(1),
                    semuaPilihan.get(2), semuaPilihan.get(3),
                    jawabanBenar
            ));
        }
        nonaktifkanTombol(false);
        tampilkanSoal();
    }

    private void nonaktifkanTombol(boolean kunci) {
        btnA.setEnabled(!kunci);
        btnB.setEnabled(!kunci);
        btnC.setEnabled(!kunci);
        btnD.setEnabled(!kunci);
    }

    private void tampilkanSoal() {
        if (soalSekarang >= daftarSoal.size()) {
            akhiriKuis();
            return;
        }

        sudahMenjawab = false;
        SoalModel soal = daftarSoal.get(soalSekarang);

        tvCounter.setText("Question " + (soalSekarang + 1) + " / " + daftarSoal.size());
        tvScore.setText("⭐ " + skorSementara);
        tvQuestion.setText(soal.pertanyaan);
        btnA.setText(soal.pilA);
        btnB.setText(soal.pilB);
        btnC.setText(soal.pilC);
        btnD.setText(soal.pilD);

        resetWarnaTombol();
        mulaiTimer();
    }

    private void cekJawaban(MaterialButton tombolDipilih) {
        if (sudahMenjawab) return;
        sudahMenjawab = true;
        batalkanTimer();

        SoalModel soal = daftarSoal.get(soalSekarang);
        String jawabanUser = tombolDipilih.getText().toString();

        if (jawabanUser.equals(soal.jawabanBenar)) {
            tombolDipilih.setBackgroundColor(Color.parseColor("#4CAF50"));
            tombolDipilih.setTextColor(Color.WHITE);
            skorSementara += (100 / daftarSoal.size());
            tvScore.setText("⭐ " + skorSementara);
        } else {
            tombolDipilih.setBackgroundColor(Color.parseColor("#F44336"));
            tombolDipilih.setTextColor(Color.WHITE);
        }

        tvQuestion.postDelayed(() -> {
            soalSekarang++;
            tampilkanSoal();
        }, 1500);
    }

    private void akhiriKuis() {
        int xpDidapat = skorSementara * 2;
        int xpLama = dbHelper.getTotalXP();
        int playerLevelLama = (xpLama / 1000) + 1;

        String tanggalSekarang = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        String levelTeks = "Level " + levelKuis;
        dbHelper.insertHistory(kategoriKuis, levelTeks, skorSementara, xpDidapat, tanggalSekarang);

        int xpBaru = dbHelper.getTotalXP();
        int playerLevelBaru = (xpBaru / 1000) + 1;

        if (playerLevelBaru > playerLevelLama) {
            kirimNotifikasiPlayerLevel(playerLevelBaru);
        }

        boolean naikLevel = false;
        if (skorSementara >= 80) {
            dbHelper.unlockNextLevel(kategoriKuis, levelKuis + 1);
            naikLevel = true;
            kirimNotifikasiLevelUp(levelKuis + 1);
        }

        String pesan = "Final Score: " + skorSementara + "\nXP Earned: +" + xpDidapat + " XP\n\n";
        pesan += naikLevel ? "🎉 AWESOME! Level " + (levelKuis + 1) + " Unlocked!"
                : "⚠️ Oops! You need a score of 80 to unlock the next level.";

        new AlertDialog.Builder(this)
                .setTitle("Quiz Cleared!")
                .setMessage(pesan)
                .setCancelable(false)
                .setPositiveButton("Finish", (dialog, which) -> finish())
                .show();
    }

    private void mulaiTimer() {
        pbTimer.setMax(100);
        pbTimer.setProgress(100);
        timer = new CountDownTimer(WAKTU_PER_SOAL, 150) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((millisUntilFinished * 100) / WAKTU_PER_SOAL);
                pbTimer.setProgress(progress);
            }
            @Override
            public void onFinish() {
                pbTimer.setProgress(0);
                if (!sudahMenjawab) {
                    sudahMenjawab = true;
                    Toast.makeText(QuizActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    tvQuestion.postDelayed(() -> {
                        soalSekarang++;
                        tampilkanSoal();
                    }, 1500);
                }
            }
        }.start();
    }

    private void batalkanTimer() {
        if (timer != null) timer.cancel();
    }

    private void resetWarnaTombol() {
        int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES;

        MaterialButton[] tombols = {btnA, btnB, btnC, btnD};
        for (MaterialButton btn : tombols) {
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setTextColor(isDarkMode ? Color.WHITE : Color.parseColor("#37474F"));
        }
    }

    private void showExitDialog() {
        batalkanTimer();
        new AlertDialog.Builder(this)
                .setTitle("Give up?")
                .setMessage("Are you sure you want to exit? Your quiz progress will not be saved.")
                .setPositiveButton("Yes, Exit", (dialog, which) -> finish())
                .setNegativeButton("Continue", (dialog, which) -> mulaiTimer())
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        batalkanTimer();
    }

    private void muatSoalDummy() {
        daftarSoal.clear();
        daftarSoal.add(new SoalModel("What is the result of 5 + 5?", "8", "9", "10", "11", "10"));
        daftarSoal.add(new SoalModel("What is the current capital of Indonesia?", "Surabaya", "Jakarta", "IKN", "Bandung", "Jakarta"));
        daftarSoal.add(new SoalModel("Which programming language has a coffee cup as its logo?", "Python", "PHP", "C++", "Java", "Java"));
        daftarSoal.add(new SoalModel("Which planet is closest to the Sun?", "Venus", "Earth", "Mercury", "Mars", "Mercury"));
        daftarSoal.add(new SoalModel("What does AI stand for?", "Auto Intel", "Artificial Intelligence", "Art Instance", "Aero Inline", "Artificial Intelligence"));
    }

    public static class SoalModel {
        String pertanyaan, pilA, pilB, pilC, pilD, jawabanBenar;
        public SoalModel(String p, String a, String b, String c, String d, String j) {
            pertanyaan = p; pilA = a; pilB = b; pilC = c; pilD = d; jawabanBenar = j;
        }
    }

    private void kirimNotifikasiLevelUp(int levelBaru) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isNotifEnabled = prefs.getBoolean("is_notification_enabled", false);

        if (!isNotifEnabled) return;

        String channelId = "SKILLSAGA_CHANNEL";
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(channelId, "SkillSaga Alerts", android.app.NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) notificationManager.createNotificationChannel(channel);
        }
        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Level Unlocked! 🌟")
                .setContentText("Awesome! You just unlocked Level " + levelBaru + " in " + kategoriKuis + ". Keep up the good work!")
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        if (notificationManager != null) notificationManager.notify(1002, builder.build());
    }

    private void kirimNotifikasiPlayerLevel(int levelBaru) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isNotifEnabled = prefs.getBoolean("is_notification_enabled", false);

        if (!isNotifEnabled) return;

        String gelar = "Novice Explorer";
        if (levelBaru >= 10) gelar = "Legendary Scholar 🏆👑";
        else if (levelBaru >= 7) gelar = "Master of Logic 🏅";
        else if (levelBaru >= 4) gelar = "Advanced Learner ⭐";

        String channelId = "SKILLSAGA_CHANNEL";
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(channelId, "SkillSaga Alerts", android.app.NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) notificationManager.createNotificationChannel(channel);
        }
        androidx.core.app.NotificationCompat.Builder builder = new androidx.core.app.NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.star_big_on)
                .setContentTitle("Player Level Up! 🎉")
                .setContentText("Awesome! You reached Level " + levelBaru + " and earned the title: " + gelar + "!")
                .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        if (notificationManager != null) notificationManager.notify(1003, builder.build());
    }
}