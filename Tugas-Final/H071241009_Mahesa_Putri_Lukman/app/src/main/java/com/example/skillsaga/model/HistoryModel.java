package com.example.skillsaga.model;

public class HistoryModel {
    private String category;
    private String level;
    private int score;
    private int xp;
    private String date;

    public HistoryModel(String category, String level, int score, int xp, String date) {
        this.category = category;
        this.level = level;
        this.score = score;
        this.xp = xp;
        this.date = date;
    }

    public String getCategory() { return category; }
    public String getLevel() { return level; }
    public int getScore() { return score; }
    public int getXp() { return xp; }
    public String getDate() { return date; }
}