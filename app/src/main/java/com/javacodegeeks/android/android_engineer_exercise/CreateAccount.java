package com.javacodegeeks.android.android_engineer_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {

    private ImageView backButton, emailTic, passwordTic, passwordRepeatTic;
    private EditText etEmail, etPassword, etPasswordRepeat;
    private boolean is8Char = false,
            hasNumber = false,
            hasUpper = false,
            hasLower = false,
            isPasswordValid =false,
            isEmailValid = false,
            isPasswordMatch = false;

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
                if(!etEmail.getText().toString().isEmpty()){
                    if (validateEmail(etEmail)){
                        etEmail.setBackgroundResource(R.drawable.green_check_email_border);
                        emailTic.setVisibility(View.VISIBLE);
                    } else {
                        etEmail.setBackgroundResource(R.drawable.red_border_whiteback);
                        emailTic.setVisibility(View.GONE);
                    }
                } else {
                    etEmail.setBackgroundResource(R.color.white);
                    emailTic.setVisibility(View.GONE);
                }
            }
        });

        etPassword.setOnFocusChangeListener((view, b) -> {
            if (!b){
                passwordValidation();
            }
        });

        etPasswordRepeat.setOnFocusChangeListener((view, b) -> {
            if (!b){
                passwordValidation();
            }
        });
    }

    private void passwordValidation() {
        if (!etPasswordRepeat.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty()){
            // both valid and match
            if (validatePassword(etPassword) && validatePassword(etPasswordRepeat) && validatePasswordMatch(etPassword, etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.green_check_email_border);
                passwordTic.setVisibility(View.VISIBLE);
                etPasswordRepeat.setBackgroundResource(R.drawable.green_check_email_border);
                passwordRepeatTic.setVisibility(View.VISIBLE);
            }// one of them not valid
            else if (!validatePassword(etPassword) || !validatePassword(etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordTic.setVisibility(View.GONE);
                etPasswordRepeat.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordRepeatTic.setVisibility(View.GONE);
            }// both valid but the do not match
            else if (validatePassword(etPassword) && validatePassword(etPasswordRepeat) && !validatePasswordMatch(etPassword, etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordTic.setVisibility(View.GONE);
                etPasswordRepeat.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordRepeatTic.setVisibility(View.GONE);
            }
        }else {
            etPassword.setBackgroundResource(R.color.white);
            passwordTic.setVisibility(View.GONE);
            etPasswordRepeat.setBackgroundResource(R.color.white);
            passwordRepeatTic.setVisibility(View.GONE);
        }

    }

    private boolean validatePasswordMatch(EditText etPassword, EditText etPasswordRepeat) {
        String etPasswordInput = etPassword.getText().toString();
        String etPasswordRepeatInput = etPasswordRepeat.getText().toString();

        if (etPasswordInput.equals(etPasswordRepeatInput)){
            Toast.makeText(this, "Passwords match", Toast.LENGTH_SHORT).show();
            isPasswordMatch = true;
        } else {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            isPasswordMatch = false;
        }
        return isPasswordMatch;
    }

    private boolean validatePassword(EditText password) {
        String passwordInput = password.getText().toString();

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
            isPasswordValid = true;
        } else {
            Toast.makeText(this, "Password is invalid", Toast.LENGTH_SHORT).show();
            isPasswordValid = false;
        }
        return isPasswordValid;
    }

    private boolean validateEmail(EditText etEmail) {
        String emailInput = etEmail.getText().toString();

        // check pattern with patterns method
        if (Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            isEmailValid = true;
        } else {
            isEmailValid = false;
        }
        return isEmailValid;
    }

    private void initViews() {
        backButton = findViewById(R.id.iv_back_button);
        emailTic = findViewById(R.id.iv_validEmail_tic);
        passwordTic = findViewById(R.id.iv_password_tic);
        passwordRepeatTic = findViewById(R.id.iv_repeat_tic);
        etEmail = findViewById(R.id.et_email_input);
        etPassword = findViewById(R.id.et_password_input);
        etPasswordRepeat= findViewById(R.id.et_passwordValid_input);
    }
}