package com.example.skillsaga.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.skillsaga.R;
import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private ImageView ivPhotoReal;
    private SharedPreferences prefs;
    private Uri selectedImageUri = null;
    private boolean isRevertedToAvatar = false;
    private final int[] avatarImages = {
            R.drawable.role_tank,
            R.drawable.role_fighter,
            R.drawable.role_assassin,
            R.drawable.role_mage,
            R.drawable.role_marksman,
            R.drawable.role_healer
    };

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivPhotoReal.setImageURI(selectedImageUri);

                    isRevertedToAvatar = false;

                    try {
                        getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView ivBack = findViewById(R.id.iv_back_from_edit);
        ivPhotoReal = findViewById(R.id.iv_edit_photo_real);
        CardView btnPickPhoto = findViewById(R.id.cv_btn_pick_photo);
        TextView tvRevertAvatar = findViewById(R.id.tv_revert_avatar);

        etName = findViewById(R.id.et_edit_name);
        etEmail = findViewById(R.id.et_edit_email);
        etPassword = findViewById(R.id.et_edit_password);
        MaterialButton btnSave = findViewById(R.id.btn_save_profile_changes);

        prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        etName.setText(prefs.getString("registered_name", ""));
        etEmail.setText(prefs.getString("registered_email", ""));
        etPassword.setText(prefs.getString("registered_password", ""));

        int userAvatarIndex = prefs.getInt("user_avatar_index", 3);
        String savedPhotoUri = prefs.getString("profile_photo_real_uri", null);

        if (savedPhotoUri != null) {
            ivPhotoReal.setImageURI(Uri.parse(savedPhotoUri));
        } else {
            ivPhotoReal.setImageResource(avatarImages[userAvatarIndex]);
        }

        tvRevertAvatar.setOnClickListener(v -> {
            isRevertedToAvatar = true;
            selectedImageUri = null;
            ivPhotoReal.setImageResource(avatarImages[userAvatarIndex]);
            Toast.makeText(this, "Avatar restored! Click Save Changes to confirm.", Toast.LENGTH_SHORT).show();
        });

        btnPickPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });

        ImageView ivToggle = findViewById(R.id.iv_toggle_password);
        ivToggle.setOnClickListener(v -> {
            if (etPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                ivToggle.setImageResource(R.drawable.icon_eye_opened);
            } else {
                etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ivToggle.setImageResource(R.drawable.icon_eye_closed);
            }
            etPassword.setSelection(etPassword.getText().length());
        });

        ivBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString().trim();
            String newEmail = etEmail.getText().toString().trim();
            String newPass = etPassword.getText().toString().trim();

            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(this, "Name and Email cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.isEmpty() && newPass.length() < 8) {
                etPassword.setError("Password must be at least 8 characters!");
                etPassword.requestFocus();
                return;
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("registered_name", newName);
            editor.putString("registered_email", newEmail);
            if (!newPass.isEmpty()) editor.putString("registered_password", newPass);

            if (isRevertedToAvatar) {
                editor.remove("profile_photo_real_uri");
            } else if (selectedImageUri != null) {
                editor.putString("profile_photo_real_uri", selectedImageUri.toString());
            }

            editor.apply();

            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}