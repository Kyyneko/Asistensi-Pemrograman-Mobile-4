package com.example.usingthread;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    // Variabel statis ini yang akan menyimpan satu-satunya objek gudang kita
    private static DataRepository instance;
    
    // Ini adalah rak tempat kita menaruh buku-buku
    private List<Buku> daftarBuku;

    // Constructor dibuat private agar tidak bisa sembarangan dibuat gudang baru dari luar
    private DataRepository() {
        daftarBuku = new ArrayList<>();
        
        // Memasukkan 15 dummy data (Syarat praktikum)
        for(int i = 1; i <= 15; i++) {
            Buku bukuDummy = new Buku(
                "ID_" + i, 
                "Judul Buku " + i, 
                "Penulis " + i, 
                "202" + (i % 10), 
                "Ini adalah blurb atau sinopsis panjang untuk buku ke-" + i + ".", 
                "", // Cover URI kosong dulu
                false, 
                "Fiksi", 
                4.5f
            );
            daftarBuku.add(bukuDummy);
        }
    }

    // Cara halaman lain untuk meminta akses ke gudang data ini
    public static DataRepository getInstance() {
        if (instance == null) { 
            instance = new DataRepository(); 
        }
        return instance;
    }

    // Fungsi untuk mengambil semua daftar buku (Dipakai di Home dan Favorite)
    public List<Buku> getDaftarBuku() { 
        return daftarBuku; 
    }
    
    // Fungsi untuk menambah buku baru (Dipakai di Add Book)
    public void tambahBuku(Buku buku) {
        // Menambahkan di index 0 agar buku yang baru dibuat otomatis tampil paling atas
        daftarBuku.add(0, buku); 
    }
}