package com.example.pratikum2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pratikum2.adapters.FeedAdapter;
import com.example.pratikum2.adapters.StoryAdapter;
import com.example.pratikum2.models.Post;
import com.example.pratikum2.models.Story;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private RecyclerView rvProfileFeed, rvProfileStories;
    private FeedAdapter profileFeedAdapter;
    private StoryAdapter storyAdapter;
    private List<Post> profilePostList;
    private List<Story> storyList;
    private Button btnAddPost;
    private TextView tvProfileName, tvProfileBio;
    private ImageView imgProfileAvatar;

    private final ActivityResultLauncher<Intent> postActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Post newPost = (Post) result.getData().getSerializableExtra("NEW_POST");
                    if (newPost != null) {
                        profilePostList.add(0, newPost);
                        profileFeedAdapter.notifyItemInserted(0);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        rvProfileFeed = findViewById(R.id.rv_profile_feed);
        rvProfileStories = findViewById(R.id.rv_profile_stories);
        btnAddPost = findViewById(R.id.btn_add_post);
        tvProfileName = findViewById(R.id.tv_profile_name);
        tvProfileBio = findViewById(R.id.tv_profile_bio);
        imgProfileAvatar = findViewById(R.id.img_profile_avatar);

        // Menerima data dari Intent
        String username = getIntent().getStringExtra("USERNAME");
        int profileImageRes = getIntent().getIntExtra("PROFILE_IMAGE_RES", R.drawable.img_profile);
        String profileImageUri = getIntent().getStringExtra("PROFILE_IMAGE_URI");

        // Jika username ada (artinya dibuka dari Feed), tampilkan data user tersebut
        if (username != null) {
            tvProfileName.setText(username);
            tvProfileBio.setText("This is " + username + "'s profile bio. Cat lover and enthusiast! 🐾");

            if (profileImageUri != null) {
                imgProfileAvatar.setImageURI(Uri.parse(profileImageUri));
            } else {
                imgProfileAvatar.setImageResource(profileImageRes);
            }

            // Sembunyikan tombol tambah post jika bukan profil sendiri
            if (!username.equals("Muhammad Hairi")) {
                btnAddPost.setVisibility(View.GONE);
            }
        } else {
            // Default profil sendiri
            tvProfileName.setText("Muhammad Hairi");
            tvProfileBio.setText("NIM: H071241055 | Information Systems | Cat Lover 🐾");
            imgProfileAvatar.setImageResource(R.drawable.img_profile);
        }

        // 1. SETUP STORY HIGHLIGHT (Memenuhi syarat 7 item)
        rvProfileStories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        storyList = new ArrayList<>();
        storyList.add(new Story(R.drawable.img_post1, "Meow"));
        storyList.add(new Story(R.drawable.img_post2, "Playtime"));
        storyList.add(new Story(R.drawable.img_post3, "Sleepy"));
        storyList.add(new Story(R.drawable.img_post4, "Hunter"));
        storyList.add(new Story(R.drawable.img_post1, "Food 🐟"));
        storyList.add(new Story(R.drawable.img_post2, "Friends"));
        storyList.add(new Story(R.drawable.img_post3, "QnA"));
        storyAdapter = new StoryAdapter(this, storyList);
        rvProfileStories.setAdapter(storyAdapter);

        // 2. SETUP PROFILE FEED (Memenuhi syarat 5 item)
        rvProfileFeed.setLayoutManager(new GridLayoutManager(this, 3));
        profilePostList = new ArrayList<>();

        // Data feed
        String currentUsername = (username != null) ? username : "Muhammad Hairi";
        int currentProfileRes = (username != null) ? profileImageRes : R.drawable.img_profile;

        profilePostList.add(new Post(R.drawable.img_post1, "Kucing oren emang ga ada lawan! 🐱", currentUsername, currentProfileRes));
        profilePostList.add(new Post(R.drawable.img_post2, "Lagi mode santai... #meow", currentUsername, currentProfileRes));
        profilePostList.add(new Post(R.drawable.img_post3, "Tidur siang jalan ninjaku. 😴", currentUsername, currentProfileRes));
        profilePostList.add(new Post(R.drawable.img_post4, "Berburu cicak! 🦎", currentUsername, currentProfileRes));
        profilePostList.add(new Post(R.drawable.img_post1, "Koleksi kardus baruku. Jangan sentuh! 📦", currentUsername, currentProfileRes));

        profileFeedAdapter = new FeedAdapter(this, profilePostList, true);
        rvProfileFeed.setAdapter(profileFeedAdapter);

        btnAddPost.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
            postActivityResultLauncher.launch(intent);
        });
    }
}