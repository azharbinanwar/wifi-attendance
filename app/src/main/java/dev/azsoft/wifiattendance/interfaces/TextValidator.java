package dev.azsoft.wifiattendance.interfaces;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public abstract class TextValidator implements TextWatcher {
    private final TextInputEditText textInputEditText;
    private final TextInputLayout textInputLayout;

    public TextValidator(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        this.textInputEditText = textInputEditText;
        this.textInputLayout = textInputLayout;
    }

    public abstract void validate();

    @Override
    final public void afterTextChanged(Editable s) {
        validate();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}