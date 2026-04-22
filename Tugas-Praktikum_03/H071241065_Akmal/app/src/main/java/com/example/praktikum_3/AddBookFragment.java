package com.example.praktikum_3;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddBookFragment extends Fragment {

    private ImageView ivAddCover;
    private EditText etTitle, etAuthor, etYear, etGenre, etRating, etBlurb;
    private String selectedImageUriString = "";

    // Launcher untuk membuka Galeri
    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUriString = uri.toString();
                    ivAddCover.setImageURI(uri);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        // Inisialisasi View
        ivAddCover = view.findViewById(R.id.ivAddCover);
        etTitle = view.findViewById(R.id.etTitle);
        etAuthor = view.findViewById(R.id.etAuthor);
        etYear = view.findViewById(R.id.etYear);
        etGenre = view.findViewById(R.id.etGenre);
        etRating = view.findViewById(R.id.etRating);
        etBlurb = view.findViewById(R.id.etBlurb);

        Button btnPickImage = view.findViewById(R.id.btnPickImage);
        Button btnSave = view.findViewById(R.id.btnSave);

        // Aksi tombol Pilih Gambar
        btnPickImage.setOnClickListener(v -> {
            pickImageLauncher.launch("image/*");
        });

        btnSave.setOnClickListener(v -> saveNewBook());

        return view;
    }

    private void saveNewBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String yearStr = etYear.getText().toString().trim();
        String genre = etGenre.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();
        String blurb = etBlurb.getText().toString().trim();

        // Validasi
        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty()) {
            Toast.makeText(getContext(), "Judul, Penulis, dan Tahun harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ubah tipe data Tahun dan Rating
        int year = Integer.parseInt(yearStr);
        float rating = 0.0f;
        if (!ratingStr.isEmpty()) {
            rating = Float.parseFloat(ratingStr);
        }

        String newId = String.valueOf(System.currentTimeMillis());

        // Buat objek buku baru
        Book newBook = new Book(newId, title, author, year, blurb, selectedImageUriString, rating, genre);

        // Tambahkan ke akhir list
        DummyData.bookList.add(newBook);

        Toast.makeText(getContext(), "Buku berhasil ditambahkan!", Toast.LENGTH_SHORT).show();

        etTitle.setText("");
        etAuthor.setText("");
        etYear.setText("");
        etGenre.setText("");
        etRating.setText("");
        etBlurb.setText("");
        ivAddCover.setImageResource(android.R.drawable.ic_menu_gallery);
        selectedImageUriString = "";
    }
}
