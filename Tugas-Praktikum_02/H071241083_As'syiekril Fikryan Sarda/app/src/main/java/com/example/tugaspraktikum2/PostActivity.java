package com.example.tugaspraktikum2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView ivUploadPreview = findViewById(R.id.iv_upload_preview);
        EditText etCaption = findViewById(R.id.et_caption);
        Button btnUpload = findViewById(R.id.btn_upload);

        ActivityResultLauncher<String[]> galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                resultUri -> {
                    if (resultUri != null) {
                        getContentResolver().takePersistableUriPermission(
                                resultUri,
                                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );

                        selectedImageUri = resultUri;
                        ivUploadPreview.setImageURI(selectedImageUri);
                        ivUploadPreview.setImageTintList(null);
                        ivUploadPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);                    }
                }
        );

        ivUploadPreview.setOnClickListener(v -> {
            galleryLauncher.launch(new String[]{"image/*"});
        });

        btnUpload.setOnClickListener(v -> {
            String captionInput = etCaption.getText().toString();

            if (selectedImageUri == null) {
                Toast.makeText(this, "Pilih foto dulu ya!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (captionInput.trim().isEmpty()) {
                etCaption.setError("Caption tidak boleh kosong!");
                return;
            }

            Post newPost = new Post(
                    "mahesa_1",
                    R.drawable.foto_profile,
                    selectedImageUri.toString(),
                    captionInput
            );

            DataSource.profilePosts.add(0, newPost);

            Toast.makeText(this, "Berhasil di-upload!", Toast.LENGTH_SHORT).show();

            finish();
        });
    }
}