package com.example.pratikum2.models;

import java.io.Serializable;

public class Post implements Serializable {
    private int imageResId = -1;
    private String imageUri;
    private String caption;
    private String username;
    private int userProfileResId = -1;
    private String userProfileUri;

    // Constructor for Resource IDs
    public Post(int imageResId, String caption, String username, int userProfileResId) {
        this.imageResId = imageResId;
        this.caption = caption;
        this.username = username;
        this.userProfileResId = userProfileResId;
    }

    // Constructor for URIs (Gallery)
    public Post(String imageUri, String caption, String username, String userProfileUri) {
        this.imageUri = imageUri;
        this.caption = caption;
        this.username = username;
        this.userProfileUri = userProfileUri;
    }

    public int getImageResId() { return imageResId; }
    public String getImageUri() { return imageUri; }
    public String getCaption() { return caption; }
    public String getUsername() { return username; }
    public int getUserProfileResId() { return userProfileResId; }
    public String getUserProfileUri() { return userProfileUri; }
}