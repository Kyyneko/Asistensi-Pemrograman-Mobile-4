package com.example.praktikum_3;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Book currentBook;
    private Button btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            if (currentBook.getCoverImageUri() != null && !currentBook.getCoverImageUri().isEmpty()) {
                ivDetailCover.setImageURI(Uri.parse(currentBook.getCoverImageUri()));
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