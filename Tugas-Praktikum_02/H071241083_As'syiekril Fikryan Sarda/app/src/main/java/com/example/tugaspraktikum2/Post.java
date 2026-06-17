package com.example.tugaspraktikum2; 
import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private String username;
    private int userProfileImage;
    private int postImage;
    private String caption;
    private String imageUri; 
    
    public Post(String username, int userProfileImage, int postImage, String caption) {
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.postImage = postImage;
        this.caption = caption;
        this.imageUri = null; 
    }
    
    public Post(String username, int userProfileImage, String imageUri, String caption) {
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.postImage = 0; 
        this.imageUri = imageUri;
        this.caption = caption;
    }

    protected Post(Parcel in) {
        username = in.readString();
        userProfileImage = in.readInt();
        postImage = in.readInt();
        caption = in.readString();
        imageUri = in.readString(); 
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getUsername() { return username; }
    public int getUserProfileImage() { return userProfileImage; }
    public int getPostImage() { return postImage; }
    public String getCaption() { return caption; }
    public String getImageUri() { return imageUri; } 

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(userProfileImage);
        dest.writeInt(postImage);
        dest.writeString(caption);
        dest.writeString(imageUri); 
    }
}