package dev.azsoft.wifiattendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import dev.azsoft.wifiattendance.databinding.ActivitySplashBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.utils.SharedPrefs;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivitySplashBinding.inflate(getLayoutInflater()).getRoot());
//        Objects.requireNonNull(getSupportActionBar()).hide();

        SharedPrefs.getInstance().Initialize(getApplicationContext());
        SharedPrefs prefs = SharedPrefs.getInstance();

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, prefs.getBoolean(Const.FIRST_LAUNCH, false) ? LoginActivity.class : LoginActivity.class));
            finish();
        }, 000);
    }
}