package com.example.skillsaga.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsaga.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        android.content.SharedPreferences prefs = getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);

        if (isDarkMode) {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RelativeLayout animationWrapper = findViewById(R.id.rl_animation_wrapper);
        View glowEffect = findViewById(R.id.v_glow_effect);

        ObjectAnimator levitateAnim = ObjectAnimator.ofFloat(animationWrapper, "translationY", 0f, -40f);
        levitateAnim.setDuration(1200);
        levitateAnim.setRepeatMode(ValueAnimator.REVERSE);
        levitateAnim.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator glowAnim = ObjectAnimator.ofFloat(glowEffect, "alpha", 0.2f, 1.0f);
        glowAnim.setDuration(1200);
        glowAnim.setRepeatMode(ValueAnimator.REVERSE);
        glowAnim.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(levitateAnim, glowAnim);
        animatorSet.start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 4000);
    }
}