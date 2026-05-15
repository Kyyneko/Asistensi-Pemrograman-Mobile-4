package com.example.praktikum_4;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private ArrayList<Book> bookList;
    private OnItemClickListener listener;

    // Interface untuk menangani klik pada item buku
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(ArrayList<Book> bookList, OnItemClickListener listener) {
        this.bookList = bookList;
        this.listener = listener;
    }

    // Method untuk update data saat pencarian (SearchView)
    public void filterList(ArrayList<Book> filteredList) {
        bookList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = bookList.get(position);

        holder.tvTitle.setText(currentBook.getTitle());
        holder.tvAuthor.setText(currentBook.getAuthor());
        holder.tvYear.setText(String.valueOf(currentBook.getYear()));
        holder.tvGenre.setText(currentBook.getGenre());

        // Menampilkan cover gambar jika ada URI, jika tidak biarkan default
        if (currentBook.getCoverImageUri() != null && !currentBook.getCoverImageUri().isEmpty()) {
            holder.ivCover.setImageURI(Uri.parse(currentBook.getCoverImageUri()));
        } else {
            holder.ivCover.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Set klik listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentBook);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvTitle, tvAuthor, tvYear, tvGenre;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvGenre = itemView.findViewById(R.id.tvGenre);
        }
    }
}