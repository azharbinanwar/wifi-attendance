package dev.azsoft.wifiattendance.utils;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.azsoft.wifiattendance.interfaces.TextValidator;

public class Validators {
    public static Object isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        if (email.contains(" "))
            return "White space is not allow in email";
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return "Please enter a valid mail";
        }
    }

    public static Object isValidPassword(String password) {
        if (password.contains(" "))
            return "Please enter a valid password";

        if (password.length() >= 8) {
            return true;
        } else {
            return "Please enter a valid password";
        }
    }

    public static TextValidator emailValidator(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        return new TextValidator(textInputEditText, textInputLayout) {
            @Override
            public void validate() {
                String inputText = textInputEditText.getText().toString();
                Object isValidField = isValidEmail(inputText);
                if (isValidField instanceof String) {
                    textInputLayout.setError(isValidField.toString());
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        };
    }

    public static TextValidator passwordValidator(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        return new TextValidator(textInputEditText, textInputLayout) {
            @Override
            public void validate() {
                String inputText = textInputEditText.getText().toString();
                Object isValidField = isValidPassword(inputText);
                if (isValidField instanceof String) {
                    textInputLayout.setError(isValidField.toString());
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        };
    }

}
