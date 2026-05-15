package com.example.praktikum_5;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class DetailActivity extends AppCompatActivity {

    private Book currentBook;
    private Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply theme
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi Views
        ImageView ivDetailCover = findViewById(R.id.ivDetailCover);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailAuthor = findViewById(R.id.tvDetailAuthor);
        TextView tvDetailYear = findViewById(R.id.tvDetailYear);
        TextView tvDetailGenre = findViewById(R.id.tvDetailGenre);
        TextView tvDetailRating = findViewById(R.id.tvDetailRating);
        TextView tvDetailBlurb = findViewById(R.id.tvDetailBlurb);
        btnLike = findViewById(R.id.btnLike);

        String bookId = getIntent().getStringExtra("EXTRA_BOOK_ID");

        // Cari objek buku yang sesuai di DummyData
        for (Book b : DummyData.bookList) {
            if (b.getId().equals(bookId)) {
                currentBook = b;
                break;
            }
        }

        if (currentBook != null) {
            tvDetailTitle.setText(currentBook.getTitle());
            tvDetailAuthor.setText(currentBook.getAuthor());
            tvDetailYear.setText(String.valueOf(currentBook.getYear()));
            tvDetailGenre.setText(currentBook.getGenre());
            tvDetailRating.setText("⭐ " + currentBook.getRating());
            tvDetailBlurb.setText(currentBook.getBlurb());

            String uriString = currentBook.getCoverImageUri();
            if (uriString != null && !uriString.isEmpty()) {
                if (uriString.startsWith("android.resource://")) {
                    // Ambil nama resource dari URI (misal: "laskar_pelangi")
                    String resName = uriString.substring(uriString.lastIndexOf("/") + 1);
                    int resId = getResources().getIdentifier(resName, "drawable", getPackageName());
                    if (resId != 0) {
                        ivDetailCover.setImageResource(resId);
                    } else {
                        ivDetailCover.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                } else {
                    // Untuk gambar yang dipilih dari galeri
                    ivDetailCover.setImageURI(Uri.parse(uriString));
                }
            } else {
                ivDetailCover.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            updateLikeButtonStatus();

            // Logika ketika tombol Like diklik
            btnLike.setOnClickListener(v -> {
                boolean currentStatus = currentBook.isLiked();
                currentBook.setLiked(!currentStatus);

                updateLikeButtonStatus();

                String message = currentBook.isLiked() ? "Ditambahkan ke Favorit" : "Dihapus dari Favorit";
                Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void updateLikeButtonStatus() {
        if (currentBook.isLiked()) {
            btnLike.setText("❤️ Hapus dari Favorit");
        } else {
            btnLike.setText("🤍 Tambahkan ke Favorit");
        }
    }
}