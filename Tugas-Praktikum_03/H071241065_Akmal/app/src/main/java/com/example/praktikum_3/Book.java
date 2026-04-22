package com.example.praktikum_3;

import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int year;
    private String blurb;
    private String coverImageUri;
    private boolean isLiked;

    private float rating;
    private String genre;

    // Constructor
    public Book(String id, String title, String author, int year, String blurb, String coverImageUri, float rating, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.blurb = blurb;
        this.coverImageUri = coverImageUri;
        this.isLiked = false;
        this.rating = rating;
        this.genre = genre;
    }

    // --- Getter & Setter ---
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public String getBlurb() { return blurb; }
    public String getCoverImageUri() { return coverImageUri; }
    public float getRating() { return rating; }
    public String getGenre() { return genre; }

    public boolean isLiked() { return isLiked; }
    public void setLiked(boolean liked) { isLiked = liked; }
}