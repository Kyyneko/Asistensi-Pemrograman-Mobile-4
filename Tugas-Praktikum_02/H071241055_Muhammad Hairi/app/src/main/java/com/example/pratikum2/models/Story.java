package com.example.pratikum2.models;

public class Story {
    private int storyImageResId;
    private String title;

    public Story(int storyImageResId, String title) {
        this.storyImageResId = storyImageResId;
        this.title = title;
    }

    public int getStoryImageResId() { return storyImageResId; }
    public String getTitle() { return title; }
}