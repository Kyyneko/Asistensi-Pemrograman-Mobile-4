package com.example.tugaspraktikum2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);

        de.hdodenhof.circleimageview.CircleImageView ivProfile = findViewById(R.id.iv_profile_detail);
        TextView tvUsername = findViewById(R.id.tv_username_detail);
        ImageView ivPost = findViewById(R.id.iv_post_detail);
        TextView tvCaption = findViewById(R.id.tv_caption_detail);

        Post post = getIntent().getParcelableExtra("EXTRA_POST");

        if (post != null) {
            ivProfile.setImageResource(post.getUserProfileImage());
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getCaption());

            if (post.getImageUri() != null) {
                ivPost.setImageURI(android.net.Uri.parse(post.getImageUri()));
            } else {
                ivPost.setImageResource(post.getPostImage());
            }
        }
    }
}