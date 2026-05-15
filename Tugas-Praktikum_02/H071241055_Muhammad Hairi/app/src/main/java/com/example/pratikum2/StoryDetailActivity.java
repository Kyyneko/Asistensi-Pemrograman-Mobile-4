package com.example.pratikum2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ImageView imgStoryFull = findViewById(R.id.img_story_full);
        TextView tvStoryTitleFull = findViewById(R.id.tv_story_title_full);
        ImageButton btnClose = findViewById(R.id.btn_close_story);

        int imageResId = getIntent().getIntExtra("STORY_IMAGE", 0);
        String title = getIntent().getStringExtra("STORY_TITLE");

        if (imageResId != 0) {
            imgStoryFull.setImageResource(imageResId);
        }
        tvStoryTitleFull.setText(title);

        btnClose.setOnClickListener(v -> finish());
    }
}