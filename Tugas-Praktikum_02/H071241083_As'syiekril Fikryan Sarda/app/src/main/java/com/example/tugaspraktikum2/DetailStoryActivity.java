package com.example.tugaspraktikum2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class DetailStoryActivity extends AppCompatActivity {

    private ImageView ivStoryFull;
    private TextView tvStoryTitleFull;
    private ArrayList<Story> listStories;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        ivStoryFull = findViewById(R.id.iv_story_full);
        tvStoryTitleFull = findViewById(R.id.tv_story_title_full);
        View viewTapLeft = findViewById(R.id.view_tap_left);
        View viewTapRight = findViewById(R.id.view_tap_right);

        listStories = DataSource.generateStories();

        Story clickedStory = getIntent().getParcelableExtra("EXTRA_STORY");

        if (clickedStory != null) {
            for (int i = 0; i < listStories.size(); i++) {
                if (listStories.get(i).getTitle().equals(clickedStory.getTitle())) {
                    currentIndex = i;
                    break;
                }
            }
        }

        updateStoryUI();

        viewTapRight.setOnClickListener(v -> {
            currentIndex++;
            if (currentIndex < listStories.size()) {
                updateStoryUI();
            } else {
                finish();
            }
        });

        viewTapLeft.setOnClickListener(v -> {
            currentIndex--;
            if (currentIndex >= 0) {
                updateStoryUI();
            } else {
                finish();
            }
        });
    }

    private void updateStoryUI() {
        Story currentStory = listStories.get(currentIndex);

        ivStoryFull.setImageResource(currentStory.getImage());
        tvStoryTitleFull.setText(currentStory.getTitle());
    }
}