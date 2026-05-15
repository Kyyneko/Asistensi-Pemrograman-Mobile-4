package com.example.pratikum2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.pratikum2.models.Post;

public class PostDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        ImageView imgProfile = findViewById(R.id.img_detail_profile);
        ImageView imgPost = findViewById(R.id.img_detail_post);
        TextView tvUsername = findViewById(R.id.tv_detail_username);
        TextView tvCaption = findViewById(R.id.tv_detail_caption);

        Post post = (Post) getIntent().getSerializableExtra("POST_DATA");

        if (post != null) {
            tvUsername.setText(post.getUsername());
            tvCaption.setText(post.getCaption());

            // Handle Profile Image
            if (post.getUserProfileResId() != -1) {
                imgProfile.setImageResource(post.getUserProfileResId());
            } else if (post.getUserProfileUri() != null) {
                imgProfile.setImageURI(Uri.parse(post.getUserProfileUri()));
            }

            // Handle Post Image
            if (post.getImageResId() != -1) {
                imgPost.setImageResource(post.getImageResId());
            } else if (post.getImageUri() != null) {
                imgPost.setImageURI(Uri.parse(post.getImageUri()));
            }
        }
    }
}