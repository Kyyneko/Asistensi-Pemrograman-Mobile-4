package com.example.tugaspraktikum2;

import java.util.ArrayList;

public class DataSource {

    public static ArrayList<Post> generateHomePosts() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Merkurius", R.drawable.foto1, R.drawable.foto11, "Asteroid di Merkurius"));
        posts.add(new Post("Venus", R.drawable.foto2, R.drawable.foto12, "Cuaca Venus lagi panas"));
        posts.add(new Post("Bumi", R.drawable.foto3, R.drawable.foto13, "Bumi kalau pemanasan global"));
        posts.add(new Post("Mars", R.drawable.foto4, R.drawable.foto14, "Mars tapi warna biru"));
        posts.add(new Post("Jupiter", R.drawable.foto5, R.drawable.foto15, "Brown Dwarf tapi sebesar Jupiter"));
        posts.add(new Post("Saturnus", R.drawable.foto6, R.drawable.foto16, "Cincin Saturnus berapa gram?"));
        posts.add(new Post("Uranus", R.drawable.foto7, R.drawable.foto17, "Chill Uranus"));
        posts.add(new Post("Neptunus", R.drawable.foto8, R.drawable.foto18, "Mirip Neptunus"));
        posts.add(new Post("Pluto", R.drawable.foto9, R.drawable.foto19, "Pluto jalan-jalan ke Sabuk Kuiper"));
        posts.add(new Post("Bulan", R.drawable.foto10, R.drawable.foto20, "Bulan tapi beku"));
        return posts;
    }

    public static ArrayList<Post> profilePosts = new ArrayList<Post>() {{
        add(new Post("mahesa_1", R.drawable.foto_profile, R.drawable.foto11, "Mercury"));
        add(new Post("mahesa_1", R.drawable.foto_profile, R.drawable.foto12, "Venus"));
        add(new Post("mahesa_1", R.drawable.foto_profile, R.drawable.foto13, "Earth"));
        add(new Post("mahesa_1", R.drawable.foto_profile, R.drawable.foto14, "Mars"));
        add(new Post("mahesa_1", R.drawable.foto_profile, R.drawable.foto15, "Jupiter"));
    }};

    public static ArrayList<Story> generateStories() {
        ArrayList<Story> stories = new ArrayList<>();
        stories.add(new Story("Planet1", R.drawable.foto1));
        stories.add(new Story("Planet2", R.drawable.foto2));
        stories.add(new Story("Planet3", R.drawable.foto3));
        stories.add(new Story("Planet4", R.drawable.foto4));
        stories.add(new Story("Planet5", R.drawable.foto5));
        stories.add(new Story("Planet6", R.drawable.foto6));
        stories.add(new Story("Planet7", R.drawable.foto7));
        return stories;
    }
}
