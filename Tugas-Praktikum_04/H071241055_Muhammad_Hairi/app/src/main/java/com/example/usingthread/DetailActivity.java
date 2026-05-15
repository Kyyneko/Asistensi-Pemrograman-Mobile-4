package com.example.usingthread; // INGAT: Ganti sesuai nama package Anda

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Buku bukuPilihan; // Menyimpan buku yang sedang dilihat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. Tangkap ID buku dari intent (kiriman dari BukuAdapter)
        String idBuku = getIntent().getStringExtra("BUKU_ID");

        // 2. Cari buku tersebut di dalam Gudang (DataRepository)
        for (Buku b : DataRepository.getInstance().getDaftarBuku()) {
            if (b.getId().equals(idBuku)) {
                bukuPilihan = b;
                break;
            }
        }

        // 3. Jika bukunya ketemu, tampilkan datanya
        if (bukuPilihan != null) {
            ImageView imgCover = findViewById(R.id.img_detail_cover);
            TextView tvJudul = findViewById(R.id.tv_detail_judul);
            TextView tvPenulis = findViewById(R.id.tv_detail_penulis);
            TextView tvTahunGenre = findViewById(R.id.tv_detail_tahun_genre);
            TextView tvBlurb = findViewById(R.id.tv_detail_blurb);
            Button btnLike = findViewById(R.id.btn_like);

            tvJudul.setText(bukuPilihan.getJudul());
            tvPenulis.setText("Oleh: " + bukuPilihan.getPenulis());
            tvTahunGenre.setText("Tahun: " + bukuPilihan.getTahunTerbit() + " | Genre: " + bukuPilihan.getGenre());
            tvBlurb.setText(bukuPilihan.getBlurb());

            // Tampilkan cover jika ada, jika tidak pakai gambar bawaan
            if (bukuPilihan.getCoverUri() != null && !bukuPilihan.getCoverUri().isEmpty()) {
                imgCover.setImageURI(Uri.parse(bukuPilihan.getCoverUri()));
            } else {
                // Fix: Ganti ic_menu_book dengan ic_menu_gallery yang tersedia di sistem
                imgCover.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // 4. Atur tampilan awal tombol Like
            updateTombolLike(btnLike);

            // 5. Aksi ketika tombol Like ditekan
            btnLike.setOnClickListener(v -> {
                // Balikkan statusnya: kalau true jadi false, kalau false jadi true
                bukuPilihan.setLiked(!bukuPilihan.isLiked());
                // Perbarui tampilan tombol
                updateTombolLike(btnLike);
            });
        }
    }

    // Fungsi kecil untuk mengubah teks tombol Like
    private void updateTombolLike(Button btn) {
        if (bukuPilihan.isLiked()) {
            btn.setText("Hapus dari Favorit ❤️");
        } else {
            btn.setText("Tambahkan ke Favorit 🤍");
        }
    }
}
