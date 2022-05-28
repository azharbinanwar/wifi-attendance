package dev.azsoft.wifiattendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import dev.azsoft.wifiattendance.databinding.ActivitySplashBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.utils.SharedPrefs;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPrefs.getInstance().Initialize(getApplicationContext());
        SharedPrefs prefs = SharedPrefs.getInstance();

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, prefs.getBoolean(Const.FIRST_LAUNCH, false) ? IntroActivity.class : IntroActivity.class));
            finish();
        }, 1000);
    }
}