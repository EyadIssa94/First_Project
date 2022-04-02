package com.javacodegeeks.android.android_engineer_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create btn to open the second activity using Intent.
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(view -> {
            Intent intentCreateAccount = new Intent();
            intentCreateAccount.setClass(this, CreateAccount.class);
            startActivity(intentCreateAccount);
        });
    }
}