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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private ArrayList<Book> sortedList;
    private View loadingLayout;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rvBooks);
        SearchView searchView = view.findViewById(R.id.searchView);
        loadingLayout = view.findViewById(R.id.loadingLayout);

        sortedList = new ArrayList<>(DummyData.bookList);
        Collections.reverse(sortedList);

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(sortedList, book -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_BOOK_ID", book.getId());
            startActivity(intent);
        });
        rvBooks.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Mekanisme Debounce: batalkan pencarian sebelumnya masih mengetik
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
                searchRunnable = () -> filter(newText);
                handler.postDelayed(searchRunnable, 300); // Tunggu 300ms setelah ketikan terakhir
                return true;
            }
        });

        return view;
    }

    private void filter(String text) {
        loadingLayout.setVisibility(View.VISIBLE);
        
        executor.execute(() -> {
            ArrayList<Book> filteredList = new ArrayList<>();
            // Simulasi proses
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            for (Book item : sortedList) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            handler.post(() -> {
                adapter.filterList(filteredList);
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