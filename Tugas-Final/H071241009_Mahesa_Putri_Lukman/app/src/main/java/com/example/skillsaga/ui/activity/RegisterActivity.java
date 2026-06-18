package com.example.skillsaga.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.skillsaga.database.DatabaseHelper;
import com.example.skillsaga.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNama, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvToLogin, tvRoleName, tvRoleDesc;
    private ViewPager2 vpAvatar;
    private LinearLayout layoutDots;
    private ImageView ivToggle;

    private int[] avatarImages = {R.drawable.role_tank, R.drawable.role_fighter, R.drawable.role_assassin, R.drawable.role_mage, R.drawable.role_marksman, R.drawable.role_healer};
    private String[] roleNames = {"T-800 Tank", "Combat Fighter", "Shadow Assassin", "Mystic Mage", "Elite Marksman", "Cloud Healer"};
    private String[] roleDescs = {
            "A fortress of knowledge with high learning durability.",
            "A frontline warrior brave enough to tackle difficult problems.",
            "Agile and lethal in solving quizzes in record time.",
            "Thirsty for knowledge and master of various logic tricks.",
            "A sniper always focused on the target of a perfect score.",
            "A healer of learning spirit, keeping the mind clear and focused."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = findViewById(R.id.et_register_nama);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        btnRegister = findViewById(R.id.btn_register);
        tvToLogin = findViewById(R.id.tv_to_login);

        vpAvatar = findViewById(R.id.vp_avatar_carousel);
        tvRoleName = findViewById(R.id.tv_role_name);
        tvRoleDesc = findViewById(R.id.tv_role_desc);
        layoutDots = findViewById(R.id.layout_dots);
        ivToggle = findViewById(R.id.iv_toggle_password);

        AvatarAdapter adapter = new AvatarAdapter(avatarImages);
        vpAvatar.setAdapter(adapter);

        setupDots(avatarImages.length);

        findViewById(R.id.btn_prev_avatar).setOnClickListener(v -> {
            int current = vpAvatar.getCurrentItem();
            if (current > 0) vpAvatar.setCurrentItem(current - 1);
        });

        findViewById(R.id.btn_next_avatar).setOnClickListener(v -> {
            int current = vpAvatar.getCurrentItem();
            if (current < avatarImages.length - 1) vpAvatar.setCurrentItem(current + 1);
        });

        vpAvatar.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvRoleName.setText(roleNames[position]);
                tvRoleDesc.setText(roleDescs[position]);
                updateDots(position);
            }
        });

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

        btnRegister.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences editorPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedName = editorPrefs.getString("registered_name", "");
            String savedEmail = editorPrefs.getString("registered_email", "");

            if (nama.equalsIgnoreCase(savedName)) {
                etNama.setError("This name is already registered! Please use another name or Login.");
                etNama.requestFocus();
                return;
            }

            if (email.equalsIgnoreCase(savedEmail)) {
                etEmail.setError("This email is already registered! Please use another email or Login.");
                etEmail.requestFocus();
                return;
            }

            if (password.length() < 8) {
                etPassword.setError("Password must be at least 8 characters!");
                etPassword.requestFocus();
                return;
            }

            SharedPreferences.Editor editor = editorPrefs.edit();
            editor.putString("registered_name", nama);
            editor.putString("registered_email", email);
            editor.putString("registered_password", password);
            editor.putInt("user_avatar_index", vpAvatar.getCurrentItem());
            editor.remove("profile_photo_real_uri");
            editor.apply();

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            dbHelper.clearAllData();

            SharedPreferences modulePrefs = getSharedPreferences("ModuleProgress", MODE_PRIVATE);
            modulePrefs.edit().clear().apply();

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        tvToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private static class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {
        private final int[] images;
        AvatarAdapter(int[] images) { this.images = images; }
        @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return new ViewHolder(imageView);
        }
        @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) { holder.imageView.setImageResource(images[position]); }
        @Override public int getItemCount() { return images.length; }
        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ViewHolder(ImageView iv) { super(iv); this.imageView = iv; }
        }
    }

    private void setupDots(int count) {
        layoutDots.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getDrawable(R.drawable.dot_inactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            layoutDots.addView(dot);
        }
    }

    private void updateDots(int position) {
        for (int i = 0; i < layoutDots.getChildCount(); i++) {
            ImageView dot = (ImageView) layoutDots.getChildAt(i);
            dot.setImageDrawable(getDrawable(i == position ? R.drawable.dot_active : R.drawable.dot_inactive));
        }
    }
}