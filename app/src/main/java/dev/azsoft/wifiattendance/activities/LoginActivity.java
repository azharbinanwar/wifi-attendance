package dev.azsoft.wifiattendance.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import dev.azsoft.wifiattendance.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityLoginBinding.inflate(getLayoutInflater())).getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        System.out.println("LoginActivity.onCreate: " + binding.llll.getLayoutParams().height  + " " +binding.llll.getLayoutParams().width);

    }


    private void init() {

    }
}