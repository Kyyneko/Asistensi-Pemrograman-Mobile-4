package com.example.usingthread; // INGAT: Ganti dengan nama package Anda yang ada di baris paling atas

public class Buku {
    // Variabel atau atribut buku
    private String id;
    private String judul;
    private String penulis;
    private String tahunTerbit;
    private String blurb;
    private String coverUri;
    private boolean isLiked; // Untuk menampung status favorit
    private String genre;    // Nilai plus dari dosen
    private float rating;    // Nilai plus dari dosen

    // Constructor: Cara kita membuat objek buku baru nantinya
    public Buku(String id, String judul, String penulis, String tahunTerbit, String blurb, String coverUri, boolean isLiked, String genre, float rating) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.blurb = blurb;
        this.coverUri = coverUri;
        this.isLiked = isLiked;
        this.genre = genre;
        this.rating = rating;
    }

    // --- Getter (Untuk mengambil data) dan Setter (Untuk mengubah data) ---
    public String getId() { return id; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public String getTahunTerbit() { return tahunTerbit; }
    public String getBlurb() { return blurb; }
    public String getCoverUri() { return coverUri; }
    
    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; } // Nanti dipanggil saat tombol Like ditekan
    
    public String getGenre() { return genre; }
    public float getRating() { return rating; }
}