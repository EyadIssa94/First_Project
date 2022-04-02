package com.javacodegeeks.android.android_engineer_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    private ImageView backButton;
    private EditText etEmail, etPassword, etPasswordRepeat;
    private boolean is8Char = false,
            hasNumber = false,
            hasUpper = false,
            hasLower = false,
            emptyInput = true,
            isValid =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initViews();

        backButton.setOnClickListener(view -> {
            finish();
        });

        etEmail.setOnFocusChangeListener((view, b) -> {
            if (!b){
                validateEmail(etEmail);
            }
        });

        etPassword.setOnFocusChangeListener((view, b) -> {
            if (!b){
                validatePassword(etPassword);
            }
        });

        etPasswordRepeat.setOnFocusChangeListener((view, b) -> {
            if (!b){
                validatePassword(etPasswordRepeat);
                if (!emptyInput && isValid)
                validatePasswordMatch(etPassword, etPasswordRepeat);
            }
        });

    }

    private void validatePasswordMatch(EditText etPassword, EditText etPasswordRepeat) {
        String etPasswordInput = etPassword.getText().toString();
        String etPasswordRepeatInput = etPasswordRepeat.getText().toString();

        if (etPasswordInput.equals(etPasswordRepeatInput)){
            Toast.makeText(this, "Passwords match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    private void validatePassword(EditText etPassword) {
        String passwordInput = etPassword.getText().toString();

        if (!passwordInput.isEmpty()){
            // Check for 8 characters
            if (passwordInput.length() >= 8){
                is8Char = true;
            } else {
                is8Char = false;
            }

            // Check for 1 number
            if (passwordInput.matches("(.*[0-9].*)")){
                hasNumber = true;
            } else{
                hasNumber = false;
            }

            // Check for uppercase
            if (passwordInput.matches("(.*[A-Z].*)")){
                hasUpper = true;
            } else {
                hasUpper = false;
            }

            // Check for lowercase
            if (passwordInput.matches("(.*[a-z].*)")){
                hasLower = true;
            } else {
                hasLower = false;
            }

            if (is8Char && hasNumber && hasUpper && hasLower){
                Toast.makeText(this, "Password is valid", Toast.LENGTH_SHORT).show();
                isValid = true;
            } else {
                Toast.makeText(this, "Password is invalid", Toast.LENGTH_SHORT).show();
                isValid = false;
            }
            emptyInput = false;
        } else {
            emptyInput = true;
            Toast.makeText(this, "Enter your password!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateEmail(EditText etEmail) {
        String emailInput = etEmail.getText().toString();

        if (!emailInput.isEmpty()) {
            // check pattern with patterns method
            if (Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                Toast.makeText(this, "Email is valid!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid email!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter your email!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        backButton = findViewById(R.id.iv_back_button);
        etEmail = findViewById(R.id.et_email_input);
        etPassword = findViewById(R.id.et_password_input);
        etPasswordRepeat= findViewById(R.id.et_passwordValid_input);
    }
}