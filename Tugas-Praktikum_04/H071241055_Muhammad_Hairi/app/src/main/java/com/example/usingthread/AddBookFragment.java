package com.example.usingthread; // INGAT: Ganti sesuai nama package Anda

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {

    private ImageView imgPreview;
    private String coverUriString = ""; // Variabel untuk menyimpan path/alamat gambar

    // Ini adalah fitur modern Android untuk membuka galeri dan mengambil gambar
    private final ActivityResultLauncher<String> pilihGambarLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    coverUriString = uri.toString(); // Simpan alamat gambarnya
                    imgPreview.setImageURI(uri);     // Tampilkan gambar ke layar
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        imgPreview = view.findViewById(R.id.img_preview);
        Button btnPilihGambar = view.findViewById(R.id.btn_pilih_gambar);
        Button btnSimpan = view.findViewById(R.id.btn_simpan);

        EditText etJudul = view.findViewById(R.id.et_judul);
        EditText etPenulis = view.findViewById(R.id.et_penulis);
        EditText etTahun = view.findViewById(R.id.et_tahun);
        EditText etGenre = view.findViewById(R.id.et_genre);
        EditText etBlurb = view.findViewById(R.id.et_blurb);

        // 1. Aksi saat tombol "Pilih Cover" diklik
        btnPilihGambar.setOnClickListener(v -> {
            pilihGambarLauncher.launch("image/*"); // Buka galeri, cari semua tipe gambar
        });

        // 2. Aksi saat tombol "Simpan Buku" diklik
        btnSimpan.setOnClickListener(v -> {
            String judul = etJudul.getText().toString().trim();
            String penulis = etPenulis.getText().toString().trim();

            // Validasi sederhana agar tidak menyimpan data kosong
            if (judul.isEmpty() || penulis.isEmpty()) {
                Toast.makeText(getContext(), "Judul dan Penulis wajib diisi!", Toast.LENGTH_SHORT).show();
                return; // Hentikan eksekusi kode di bawahnya
            }

            // Buat ID unik menggunakan waktu saat ini
            String idBuku = String.valueOf(System.currentTimeMillis());

            // Rakit objek buku baru
            Buku bukuBaru = new Buku(
                    idBuku,
                    judul,
                    penulis,
                    etTahun.getText().toString().trim(),
                    etBlurb.getText().toString().trim(),
                    coverUriString, // Hasil dari galeri tadi
                    false,          // Pasti false karena baru ditambahkan
                    etGenre.getText().toString().trim(),
                    0f              // Rating awal
            );

            // Masukkan ke Gudang Data
            DataRepository.getInstance().tambahBuku(bukuBaru);

            // Beri notifikasi ke pengguna
            Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

            // Kosongkan form kembali setelah berhasil disimpan
            etJudul.setText("");
            etPenulis.setText("");
            etTahun.setText("");
            etGenre.setText("");
            etBlurb.setText("");
            imgPreview.setImageResource(0);
            coverUriString = "";
        });

        return view;
    }
}