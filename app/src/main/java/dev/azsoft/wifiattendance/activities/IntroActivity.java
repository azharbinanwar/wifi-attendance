package dev.azsoft.wifiattendance.activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.adapter.IntroAdapter;
import dev.azsoft.wifiattendance.databinding.ActivityIntroBinding;
import dev.azsoft.wifiattendance.models.Intro;

public class IntroActivity extends AppCompatActivity {
    private List<Intro> introList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIntroBinding binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        IntroAdapter introAdapter = new IntroAdapter(getApplicationContext(), introList);
        binding.vpIntro.setAdapter(introAdapter);
    }

    private void init() {
        introList = new ArrayList<>();
        introList.add(new Intro(R.drawable.app_logo, "Create profile", "Create your profile and wait for admin approvel"));
        introList.add(new Intro(R.drawable.app_logo, "Start your time", "Track your time automatically"));
        introList.add(new Intro(R.drawable.app_logo, "View Statistics", "View all your statistics"));

    }
}
