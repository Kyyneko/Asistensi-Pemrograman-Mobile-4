package com.example.libraryapp; // INGAT: Ganti nama package-nya

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeFragment extends Fragment {
    
    private BukuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Sambungkan dengan desain XML
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        // 2. Siapkan RecyclerView
        RecyclerView rvBuku = view.findViewById(R.id.rv_buku);
        rvBuku.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // 3. Ambil data dari "Gudang", lalu berikan ke "Kurir" (Adapter)
        List<Buku> daftarBuku = DataRepository.getInstance().getDaftarBuku();
        adapter = new BukuAdapter(getContext(), daftarBuku);
        rvBuku.setAdapter(adapter);

        // 4. Aktifkan Fitur Pencarian berdasarkan Judul
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Saring buku setiap kali user mengetik
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}