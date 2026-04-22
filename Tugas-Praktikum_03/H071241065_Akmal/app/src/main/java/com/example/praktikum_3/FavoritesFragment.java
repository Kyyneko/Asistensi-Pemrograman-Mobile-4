package com.example.praktikum_3;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private BookAdapter adapter;
    private ArrayList<Book> favoriteBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // onResume akan dipanggil setiap kali halaman ini tampil di layar
    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        favoriteBooks = new ArrayList<>();

        // Looping untuk mencari buku yang isLiked() == true
        for (Book book : DummyData.bookList) {
            if (book.isLiked()) {
                favoriteBooks.add(book);
            }
        }

        adapter = new BookAdapter(favoriteBooks, book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK_ID", book.getId());
            startActivity(intent);
        });

        rvFavorites.setAdapter(adapter);
    }
}