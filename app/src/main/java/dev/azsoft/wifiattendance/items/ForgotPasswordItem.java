package dev.azsoft.wifiattendance.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import dev.azsoft.wifiattendance.R;

public class ForgotPasswordItem extends DialogFragment {
    public static String TAG = "ForgotPasswordItem";
    double widthPercentage, heightPercentage;

    public ForgotPasswordItem(double widthPercentage, double heightPercentage) {
        this.heightPercentage = heightPercentage;
        this.widthPercentage = widthPercentage;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).getWindow().setBackgroundDrawableResource(R.drawable.dialog_round_corner_background);
        return inflater.inflate(R.layout.forgot_password_item, container, false);
    }

    public double getWidthPercentage() {
        if (widthPercentage == -1 || widthPercentage == -2) {
            return widthPercentage;
        } else {
            return getResources().getDisplayMetrics().widthPixels * widthPercentage;
        }
    }

    public double getHeightPercentage() {
        if (heightPercentage == -1 || heightPercentage == -2) {
            return heightPercentage;
        } else {
            return getResources().getDisplayMetrics().heightPixels * heightPercentage;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout((int) getWidthPercentage(), (int) getHeightPercentage());
    }
}

