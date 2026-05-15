package com.example.pratikum2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pratikum2.models.Post;

public class PostActivity extends AppCompatActivity {

    private EditText etCaption;
    private Button btnUpload, btnSelectImage;
    private ImageView imgPostPreview;
    private Uri selectedImageUri;

    // Launcher untuk membuka galeri (Photo Picker modern)
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imgPostPreview.setImageURI(uri);
                    
                    // Penting: Berikan izin akses permanen ke URI ini agar bisa dibaca lagi nanti (opsional untuk session ini)
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    Toast.makeText(this, "Tidak ada foto yang dipilih", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        etCaption = findViewById(R.id.et_post_caption);
        btnUpload = findViewById(R.id.btn_upload_post);
        btnSelectImage = findViewById(R.id.btn_select_image);
        imgPostPreview = findViewById(R.id.img_post_preview);

        btnSelectImage.setOnClickListener(v -> {
            // Membuka galeri hanya untuk gambar
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnUpload.setOnClickListener(v -> {
            String caption = etCaption.getText().toString();

            if (selectedImageUri == null) {
                Toast.makeText(this, "Silakan pilih foto terlebih dahulu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (caption.isEmpty()) {
                Toast.makeText(this, "Caption tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat objek post baru dengan URI gallery
            // Parameter constructor: (imageUri, caption, username, userProfileUri)
            // Di sini userProfileUri kita kirim null karena profile masih pakai Resource ID (img_profile)
            // Namun FeedAdapter sudah kita siapkan untuk menangani Post dengan Res ID atau URI.
            
            Post newPost = new Post(
                    selectedImageUri.toString(),
                    caption,
                    "Muhammad Hairi",
                    null // Profile masih pakai Resource ID (img_profile) di ProfileActivity
            );

            // Kirim data kembali ke ProfileActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NEW_POST", newPost);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}