package com.javacodegeeks.android.android_engineer_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private ImageView backButton, emailTic, passwordTic, passwordRepeatTic;
    private EditText etEmail, etPassword, etPasswordRepeat;
    private TextView emailError, passwordInvalid, passwordsMatchError;
    private Button nextButton;
    ArrayList<String> emailList = new ArrayList<String>();
    private boolean is8Char         = false,
                    hasNumber       = false,
                    hasUpper        = false,
                    hasLower        = false,
                    isPasswordValid = false,
                    isEmailValid    = false,
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
                emailFormatValidation();
                emailListCheck(etEmail);
                nextBtnActivation();
            }
        });

        etPassword.setOnFocusChangeListener((view, b) -> {
            if (!b){
                passwordValidation();
                nextBtnActivation();
            }
        });

        etPasswordRepeat.setOnFocusChangeListener((view, b) -> {
            if (!b){
                passwordValidation();
                nextBtnActivation();
            }
        });
    }

    private boolean emailListCheck(EditText etEmail) {
        String emailInput = etEmail.getText().toString();
        boolean isInList = false;

        if (!emailInput.isEmpty()){
            if (emailList.contains(emailInput)) {
                isInList = true;
                etEmail.setBackgroundResource(R.drawable.red_border_whiteback);
                emailTic.setVisibility(View.GONE);
                emailError.setVisibility(View.VISIBLE);
                nextButton.setAlpha(.5F);
                nextButton.setClickable(false);
            } else {
                isInList = false;
                etEmail.setBackgroundResource(R.drawable.green_check_email_border);
                emailTic.setVisibility(View.VISIBLE);
                emailError.setVisibility(View.GONE);
            }
        } else {
            etEmail.setBackgroundResource(R.color.white);
            emailTic.setVisibility(View.GONE);
        }
        return isInList;
    }

    private void nextBtnActivation(){
        if (validateEmail(etEmail) && !emailListCheck(etEmail) && validatePassword(etPasswordRepeat) &&
                validatePasswordMatch(etPassword, etPasswordRepeat)){
            nextButton.setAlpha(1);
            nextButton.setClickable(true);

            nextButton.setOnClickListener(view -> {
                String emailInput = etEmail.getText().toString();
                emailList.add(emailInput);
                Toast.makeText(this, "Email was added to the list!", Toast.LENGTH_SHORT).show();
            });
        } else {
            nextButton.setAlpha(.5F);
            nextButton.setClickable(false);
        }
    }

    private void emailFormatValidation() {
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

    private void passwordValidation() {
        if (!etPasswordRepeat.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty()){
            // both valid and match
            if (validatePassword(etPassword) && validatePassword(etPasswordRepeat) && validatePasswordMatch(etPassword, etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.green_check_email_border);
                passwordTic.setVisibility(View.VISIBLE);
                etPasswordRepeat.setBackgroundResource(R.drawable.green_check_email_border);
                passwordRepeatTic.setVisibility(View.VISIBLE);
                passwordInvalid.setVisibility(View.GONE);
                passwordsMatchError.setVisibility(View.GONE);
            }// one of them not valid
            else if (!validatePassword(etPassword) || !validatePassword(etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordTic.setVisibility(View.GONE);
                etPasswordRepeat.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordRepeatTic.setVisibility(View.GONE);
                passwordInvalid.setVisibility(View.VISIBLE);
                passwordsMatchError.setVisibility(View.GONE);
            }// both valid but the do not match
            else if (validatePassword(etPassword) && validatePassword(etPasswordRepeat) && !validatePasswordMatch(etPassword, etPasswordRepeat)){
                etPassword.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordTic.setVisibility(View.GONE);
                etPasswordRepeat.setBackgroundResource(R.drawable.red_border_whiteback);
                passwordRepeatTic.setVisibility(View.GONE);
                passwordInvalid.setVisibility(View.GONE);
                passwordsMatchError.setVisibility(View.VISIBLE);
            }
        }else {
            etPassword.setBackgroundResource(R.color.white);
            passwordTic.setVisibility(View.GONE);
            etPasswordRepeat.setBackgroundResource(R.color.white);
            passwordRepeatTic.setVisibility(View.GONE);
            passwordInvalid.setVisibility(View.GONE);
            passwordsMatchError.setVisibility(View.GONE);
        }

    }

    private boolean validatePasswordMatch(EditText etPassword, EditText etPasswordRepeat) {
        String etPasswordInput = etPassword.getText().toString();
        String etPasswordRepeatInput = etPasswordRepeat.getText().toString();

        if (etPasswordInput.equals(etPasswordRepeatInput)){
            isPasswordMatch = true;
        } else {
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
            isPasswordValid = true;
        } else {
            isPasswordValid = false;
        }
        return isPasswordValid;
    }

    private boolean validateEmail(EditText etEmail) {
        String emailInput = etEmail.getText().toString();

        // check pattern with patterns method
        if (Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            isEmailValid = true;
//            if (emailList.contains(emailInput)){
//                emailError.setVisibility(View.VISIBLE);
//                etEmail.setBackgroundResource(R.drawable.red_border_whiteback);
//                emailTic.setVisibility(View.GONE);
//                isEmailValid = false;
//            } else {
//                emailError.setVisibility(View.GONE);
//                etEmail.setBackgroundResource(R.color.white);
//                emailTic.setVisibility(View.VISIBLE);
//                isEmailValid = true;
//            }
        } else {
            isEmailValid = false;
        }
        return isEmailValid;
    }

    private void initViews() {
        backButton          = findViewById(R.id.iv_back_button);
        emailTic            = findViewById(R.id.iv_validEmail_tic);
        passwordTic         = findViewById(R.id.iv_password_tic);
        passwordRepeatTic   = findViewById(R.id.iv_repeat_tic);
        etEmail             = findViewById(R.id.et_email_input);
        etPassword          = findViewById(R.id.et_password_input);
        etPasswordRepeat    = findViewById(R.id.et_passwordValid_input);
        nextButton          = findViewById(R.id.btn_next);
        emailError          = findViewById(R.id.tv_email_error);
        passwordInvalid     = findViewById(R.id.tv_password_not_valid);
        passwordsMatchError = findViewById(R.id.tv_password_no_match);
    }
}