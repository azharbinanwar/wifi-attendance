package dev.azsoft.wifiattendance.activities.user;

import static dev.azsoft.wifiattendance.global.Const.DEFAULT_DIALOG_WIDTH;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.Repository;
import dev.azsoft.wifiattendance.activities.dashboard.DashboardActivity;
import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.databinding.ActivityLoginBinding;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.interfaces.TextValidator;
import dev.azsoft.wifiattendance.items.ForgotPasswordItem;
import dev.azsoft.wifiattendance.models.Employee;
import dev.azsoft.wifiattendance.utils.Utils;
import dev.azsoft.wifiattendance.utils.Validators;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private TextValidator emailValidator;
    private Repository repository = Repository.getInstance();
    private SharedPrefs sharedPrefs = SharedPrefs.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityLoginBinding.inflate(getLayoutInflater())).getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.btnForgotPassword.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.edtEmail.addTextChangedListener(Validators.emailValidator(binding.edtEmail, binding.textInputLayoutEmail));
        binding.edtPassword.addTextChangedListener(Validators.passwordValidator(binding.edtPassword, binding.textInputLayoutPassword));
    }


    private void onLogIn() {
        Utils.onHideKeyboard(this);
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();
        if (Validators.isValidEmail(email) instanceof Boolean && Validators.isValidPassword(password) instanceof Boolean) {
            binding.btnLogin.setProcessing(true);
            repository.onLogIn(true, email, password, this::onLoginSuccessful);
        }
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

    private void onLoginSuccessful(Object ob) {
        if (ob instanceof Employee) {
            sharedPrefs.setString(Const.CURRENT_USER, ob.toString());
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        } else if (ob instanceof Exception) {
            Toast.makeText(this, ((Exception) ob).getMessage(), Toast.LENGTH_SHORT).show();
        }
        binding.btnLogin.setProcessing(false);
    }
}