package com.example.pratikum2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pratikum2.adapters.FeedAdapter;
import com.example.pratikum2.models.Post;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvFeed;
    private FeedAdapter feedAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvFeed = findViewById(R.id.rv_home_feed);
        rvFeed.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();

        // Memenuhi syarat minimal 10 item feed
        postList.add(new Post(
                R.drawable.img_post1,
                "Lagi asik main sama si oren, gemoy banget! 🐱 #catlover",
                "Muhammad Hairi",
                R.drawable.img_profile
        ));

        postList.add(new Post(
                R.drawable.img_post2,
                "Melihat dunia dari sudut pandang yang berbeda. #instacat #meow",
                "Ahmad_Zaki",
                R.drawable.img_post2
        ));

        postList.add(new Post(
                R.drawable.img_post3,
                "Mode mager di hari Minggu. Jangan diganggu! 😴",
                "Siti_Aminah",
                R.drawable.img_post3
        ));

        postList.add(new Post(
                R.drawable.img_post4,
                "Siap-siap berburu cicak malam ini. Wish me luck! 🦎",
                "Budi_Santoso",
                R.drawable.img_post4
        ));

        postList.add(new Post(
                R.drawable.img_post1,
                "Ekspresi ketika sadar besok sudah hari Senin. 🙀",
                "Dewi_Lestari",
                R.drawable.img_post1
        ));

        postList.add(new Post(
                R.drawable.img_post2,
                "Cemilan mana cemilan? Majikan pelit banget hari ini. 😿",
                "Rizky_Ramadhan",
                R.drawable.img_post2
        ));

        postList.add(new Post(
                R.drawable.img_post3,
                "Menikmati sinar matahari pagi. Hangat dan nyaman. ☀️",
                "Anisa_Fitri",
                R.drawable.img_post3
        ));

        postList.add(new Post(
                R.drawable.img_post4,
                "Lagi mantau mangsa dari atas lemari. Jangan berisik! 😼",
                "Kucing_Oren_Barbar",
                R.drawable.img_post4
        ));

        postList.add(new Post(
                R.drawable.img_post1,
                "Kapan ya dikasih makan ikan tuna lagi? 🐟",
                "Si_Bulu_Lebat",
                R.drawable.img_post1
        ));

        postList.add(new Post(
                R.drawable.img_post2,
                "Hari ini capek banget, mau rebahan aja seharian. 😽",
                "Pecinta_Kardus",
                R.drawable.img_post2
        ));

        feedAdapter = new FeedAdapter(this, postList);
        rvFeed.setAdapter(feedAdapter);
    }
}