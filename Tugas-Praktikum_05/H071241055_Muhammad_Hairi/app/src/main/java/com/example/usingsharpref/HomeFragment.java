package com.example.usingsharpref; // INGAT: Ganti sesuai nama package Anda!

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private BukuAdapter adapter;
    private ProgressBar progressBar;

    // Alat untuk menjalankan background thread
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView rvBuku = view.findViewById(R.id.rv_buku);
        rvBuku.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load awal data (Tampilkan semua buku)
        List<Buku> semuaBuku = DataRepository.getInstance().getDaftarBuku();
        adapter = new BukuAdapter(getContext(), new ArrayList<>(semuaBuku));
        rvBuku.setAdapter(adapter);

        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Jalankan proses pencarian di Background Thread
                jalankanPencarianBackground(newText);
                return true;
            }
        });

        return view;
    }

    private void jalankanPencarianBackground(String keyword) {
        // 1. Munculkan ProgressBar (Tanda loading mulai)
        progressBar.setVisibility(View.VISIBLE);

        // 2. Lempar proses ke Background Thread
        executor.execute(() -> {
            try {
                // Fake Delay 500 milidetik agar ProgressBar terlihat berputar
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Proses menyaring data
            List<Buku> semuaBuku = DataRepository.getInstance().getDaftarBuku();
            List<Buku> bukuTersaring = new ArrayList<>();

            if (keyword == null || keyword.trim().isEmpty()) {
                bukuTersaring.addAll(semuaBuku);
            } else {
                String filterPattern = keyword.toLowerCase().trim();
                for (Buku item : semuaBuku) {
                    if (item.getJudul().toLowerCase().contains(filterPattern)) {
                        bukuTersaring.add(item);
                    }
                }
            }

            // 3. Kembalikan data ke UI Thread (Layar Utama)
            handler.post(() -> {
                // Sembunyikan ProgressBar
                progressBar.setVisibility(View.GONE);

                // Update tampilan daftar buku
                if (adapter != null) {
                    adapter.updateData(bukuTersaring);
                }
            });
        });
    }

    // Refresh data jika ada buku baru ditambahkan / dihapus saat kembali ke layar ini
    @Override
    public void onResume() {
        super.onResume();
        jalankanPencarianBackground(""); // Otomatis me-load semua buku dari background
    }
}