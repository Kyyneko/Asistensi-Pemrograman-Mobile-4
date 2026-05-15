package com.example.praktikum_3;

import java.util.ArrayList;

public class DummyData {

    public static ArrayList<Book> bookList = new ArrayList<>();

    public static void initData() {
        if (!bookList.isEmpty()) return;

        String path = "android.resource://com.example.praktikum_3/drawable/";

        bookList.add(new Book("1", "Laskar Pelangi", "Andrea Hirata", 2005, "Kisah perjuangan 10 anak Belitong dalam mengejar pendidikan.", path + "laskar_pelangi", 4.8f, "Fiksi / Inspiratif"));
        bookList.add(new Book("2", "Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Kisah cinta Minke dan Annelies di masa kolonial Belanda.", path + "bumi_manusia", 4.9f, "Fiksi Sejarah"));
        bookList.add(new Book("3", "Cantik Itu Luka", "Eka Kurniawan", 2002, "Epos keluarga yang memadukan sejarah Indonesia dengan realisme magis.", path + "cantik_itu_luka", 4.7f, "Fiksi"));
        bookList.add(new Book("4", "Laut Bercerita", "Leila S. Chudori", 2017, "Kisah tentang persahabatan, cinta, dan pengkhianatan di masa Orde Baru.", path + "laut_bercerita", 4.8f, "Fiksi Sejarah"));
        bookList.add(new Book("5", "Hujan", "Tere Liye", 2016, "Kisah tentang persahabatan, cinta, dan melupakan di masa depan.", path + "hujan", 4.6f, "Fiksi Ilmiah / Romance"));
        bookList.add(new Book("6", "Pulang", "Tere Liye", 2015, "Perjalanan Bujang dari pedalaman Sumatera menjadi 'Shadow Economy'.", path + "pulang", 4.7f, "Aksi / Thriller"));
        bookList.add(new Book("7", "Aroma Karsa", "Dee Lestari", 2018, "Pencarian pusaka misterius yang berhubungan dengan indra penciuman.", path + "aroma_karsa", 4.8f, "Fantasi / Misteri"));
        bookList.add(new Book("8", "Supernova: Kesatria, Putri, dan Bintang Jatuh", "Dee Lestari", 2001, "Romansa yang terjalin dalam dimensi sains dan spiritualitas.", path + "supernova", 4.5f, "Fiksi Ilmiah"));
        bookList.add(new Book("9", "Perahu Kertas", "Dee Lestari", 2009, "Kisah pasang surut hubungan Kugy dan Keenan.", path + "perahu_kertas", 4.6f, "Romance / Young Adult"));
        bookList.add(new Book("10", "Orang-Orang Biasa", "Andrea Hirata", 2019, "Sekelompok orang miskin yang merencanakan perampokan bank.", path + "orang_orang_biasa", 4.4f, "Fiksi / Komedi"));
        bookList.add(new Book("11", "Gadis Kretek", "Ratih Kumala", 2012, "Perjalanan menyusuri rahasia keluarga dan industri kretek di Indonesia.", path + "gadis_kretek", 4.7f, "Fiksi Sejarah"));
        bookList.add(new Book("12", "Ronggeng Dukuh Paruk", "Ahmad Tohari", 1982, "Kisah cinta Srintil seorang ronggeng dan Rasus.", path + "ronggeng", 4.8f, "Sastra Klasik"));
        bookList.add(new Book("13", "Bumi", "Tere Liye", 2014, "Petualangan Raib, Seli, dan Ali di dunia paralel.", path + "bumi", 4.7f, "Fantasi"));
        bookList.add(new Book("14", "Dilan 1990", "Pidi Baiq", 2014, "Kisah romansa masa SMA di Bandung pada tahun 1990.", path + "dilan_1990", 4.5f, "Romance / Remaja"));
        bookList.add(new Book("15", "Negeri 5 Menara", "A. Fuadi", 2009, "Kisah 6 santri dari berbagai daerah di pondok pesantren.", path + "negeri_5_menara", 4.6f, "Fiksi / Inspiratif"));
    }
}