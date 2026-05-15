package com.example.praktikum_4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private View loadingLayout;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        loadingLayout = view.findViewById(R.id.loadingLayout);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        loadingLayout.setVisibility(View.VISIBLE);
        
        executor.execute(() -> {
            ArrayList<Book> favoriteBooks = new ArrayList<>();
            // Simulasi pemrosesan berat
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Book book : DummyData.bookList) {
                if (book.isLiked()) {
                    favoriteBooks.add(book);
                }
            }

            handler.post(() -> {
                adapter = new BookAdapter(favoriteBooks, book -> {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("EXTRA_BOOK_ID", book.getId());
                    startActivity(intent);
                });
                rvFavorites.setAdapter(adapter);
                loadingLayout.setVisibility(View.GONE);
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}