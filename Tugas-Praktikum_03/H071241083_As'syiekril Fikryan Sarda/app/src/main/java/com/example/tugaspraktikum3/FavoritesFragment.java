package com.example.tugaspraktikum3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites);
        tvEmpty = view.findViewById(R.id.tv_empty_favorites);

        setupRecyclerView();

        return view;
    }

    // INI KUNCI UTAMANYA KAWAN:
    // onResume() akan selalu dipanggil setiap kali halaman ini dibuka
    // entah dari Bottom Navigation atau saat kembali dari DetailActivity
    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void setupRecyclerView() {
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi adapter dengan list kosong dulu
        adapter = new BookAdapter(new ArrayList<>());
        rvFavorites.setAdapter(adapter);

        // Kalau buku favorit diklik, buka juga DetailActivity-nya
        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK", data);
            startActivity(intent);
        });
    }

    // Fungsi untuk menyaring buku yang di-like
    private void loadFavoriteBooks() {
        ArrayList<Book> favoriteBooks = new ArrayList<>();

        // Loop semua buku di database sementara (MainActivity.libraryBooks)
        for (Book book : MainActivity.libraryBooks) {
            if (book.isLiked()) { // Kalau status likenya True, masukkan ke keranjang
                favoriteBooks.add(book);
            }
        }

        // Lempar data yang sudah disaring ke Adapter
        adapter.setFilteredList(favoriteBooks);

        // Mengatur kapan harus memunculkan teks "Kosong" atau List Buku
        if (favoriteBooks.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        }
    }
}