package com.example.usingthread;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> implements Filterable {
    
    private Context context;
    private List<Buku> bukuList;
    private List<Buku> bukuListFull;

    public BukuAdapter(Context context, List<Buku> bukuList) {
        this.context = context;
        this.bukuList = bukuList;
        this.bukuListFull = new ArrayList<>(bukuList); 
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buku, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buku buku = bukuList.get(position);
        
        holder.tvJudul.setText(buku.getJudul());
        holder.tvPenulis.setText(buku.getPenulis());
        holder.tvGenreRating.setText("Genre: " + buku.getGenre() + " | ★ " + buku.getRating());
        
        if (buku.getCoverUri() != null && !buku.getCoverUri().isEmpty()) {
            holder.imgCover.setImageURI(Uri.parse(buku.getCoverUri()));
        } else {
            // Fix: Replace non-existent ic_menu_book with ic_menu_gallery
            holder.imgCover.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("BUKU_ID", buku.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { 
        return bukuList.size(); 
    }
    // Method baru untuk memperbarui data dari Background Thread
    public void updateData(List<Buku> dataBaru) {
        bukuList.clear();
        bukuList.addAll(dataBaru);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Buku> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(bukuListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Buku item : bukuListFull) {
                        if (item.getJudul().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                bukuList.clear();
                bukuList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvPenulis, tvGenreRating;
        ImageView imgCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPenulis = itemView.findViewById(R.id.tv_penulis);
            tvGenreRating = itemView.findViewById(R.id.tv_genre_rating);
            imgCover = itemView.findViewById(R.id.img_cover);
        }
    }
}
