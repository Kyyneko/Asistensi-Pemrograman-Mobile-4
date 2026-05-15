package com.example.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum_3.Book;
import com.example.praktikum_3.BookAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ArrayList<Book> sortedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rvBooks);
        SearchView searchView = view.findViewById(R.id.searchView);

        // Mengambil data dari DummyData
        sortedList = new ArrayList<>(DummyData.bookList);

        Collections.reverse(sortedList);

        // Setup RecyclerView
        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(sortedList, book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK_ID", book.getId());
            startActivity(intent);
        });
        rvBooks.setAdapter(adapter);

        // Setup Logika SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return view;
    }

    // Method untuk memfilter data berdasarkan inputan pencarian
    private void filter(String text) {
        ArrayList<Book> filteredList = new ArrayList<>();
        for (Book item : sortedList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}