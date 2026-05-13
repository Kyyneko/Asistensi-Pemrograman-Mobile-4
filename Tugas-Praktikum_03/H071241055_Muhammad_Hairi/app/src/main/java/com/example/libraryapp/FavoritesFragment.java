package com.example.libraryapp; // INGAT: Ganti sesuai nama package Anda

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
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvBukuFavorit;
    private BukuAdapter adapter;
    private List<Buku> daftarFavorit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Sambungkan dengan desain XML
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // 2. Siapkan RecyclerView
        rvBukuFavorit = view.findViewById(R.id.rv_buku_favorit);
        rvBukuFavorit.setLayoutManager(new LinearLayoutManager(getContext()));

        // 3. Siapkan daftar kosong dan pasang Adapter
        daftarFavorit = new ArrayList<>();
        adapter = new BukuAdapter(getContext(), daftarFavorit);
        rvBukuFavorit.setAdapter(adapter);

        return view;
    }

    // Method ini dipanggil setiap kali halaman ini tampil di layar
    @Override
    public void onResume() {
        super.onResume();

        // 1. Kosongkan daftar favorit yang lama agar tidak dobel
        daftarFavorit.clear();

        // 2. Ambil SEMUA buku dari Gudang (DataRepository)
        List<Buku> semuaBuku = DataRepository.getInstance().getDaftarBuku();

        // 3. Saring: Jika buku tersebut isLiked() == true, masukkan ke daftar favorit
        for (Buku buku : semuaBuku) {
            if (buku.isLiked()) {
                daftarFavorit.add(buku);
            }
        }

        // 4. Beritahu kurir (Adapter) kalau datanya sudah berubah biar layarnya di-refresh
        adapter.notifyDataSetChanged();
    }
}