package dev.azsoft.wifiattendance.activities;

import static dev.azsoft.wifiattendance.global.Const.DEFAULT_DIALOG_WIDTH;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.databinding.ActivityLoginBinding;
import dev.azsoft.wifiattendance.items.ForgotPasswordItem;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding;
        setContentView((binding = ActivityLoginBinding.inflate(getLayoutInflater())).getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.btnForgotPassword.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnLogin.setText("how are you");
    }


    private void onLogIn() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    private void onForgotPassword() {
        new ForgotPasswordItem(DEFAULT_DIALOG_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT).show(getSupportFragmentManager(), ForgotPasswordItem.TAG);
    }

    @Override
    public void onClick(View view) {
        int clickedId = view.getId();
        if (clickedId == R.id.btn_forgot_password) onForgotPassword();
        else if (clickedId == R.id.btn_login) onLogIn();
    }
}