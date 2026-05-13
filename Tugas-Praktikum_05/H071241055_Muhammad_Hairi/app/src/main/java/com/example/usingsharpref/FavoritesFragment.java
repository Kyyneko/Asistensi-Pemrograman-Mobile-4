package com.example.usingsharpref; // INGAT: Ganti sesuai nama package Anda!

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

    private BukuAdapter adapter;
    private ProgressBar progressBarFav;

    // Alat untuk menjalankan background thread
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        progressBarFav = view.findViewById(R.id.progress_bar_fav);

        RecyclerView rvBukuFavorit = view.findViewById(R.id.rv_buku_favorit);
        rvBukuFavorit.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BukuAdapter(getContext(), new ArrayList<>());
        rvBukuFavorit.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 1. Munculkan ProgressBar (Tanda loading mulai) [cite: 32]
        progressBarFav.setVisibility(View.VISIBLE);

        // 2. Lempar proses load data ke Background Thread [cite: 31]
        executor.execute(() -> {
            try {
                // Fake Delay 600 milidetik agar animasi loading terlihat elegan
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Ambil semua buku, lalu saring yang Favorit saja
            List<Buku> semuaBuku = DataRepository.getInstance().getDaftarBuku();
            List<Buku> daftarFavorit = new ArrayList<>();

            for (Buku buku : semuaBuku) {
                if (buku.isLiked()) {
                    daftarFavorit.add(buku);
                }
            }

            // 3. Kembalikan data ke UI Thread (Layar Utama)
            handler.post(() -> {
                // Sembunyikan ProgressBar karena data sudah selesai [cite: 32]
                progressBarFav.setVisibility(View.GONE);

                // Update tampilan daftar buku
                if (adapter != null) {
                    adapter.updateData(daftarFavorit);
                }
            });
        });
    }
}