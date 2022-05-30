package dev.azsoft.wifiattendance.activities;

import static dev.azsoft.wifiattendance.global.Const.DEFAULT_DIALOG_WIDTH;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import dev.azsoft.wifiattendance.databinding.ActivityLoginBinding;
import dev.azsoft.wifiattendance.items.ForgotPasswordItem;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityLoginBinding.inflate(getLayoutInflater())).getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        binding.btnForgotPassword.setOnClickListener(view -> {
            new ForgotPasswordItem(DEFAULT_DIALOG_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT).show(getSupportFragmentManager(), ForgotPasswordItem.TAG);
        });

    }


    private void init() {

    }
}