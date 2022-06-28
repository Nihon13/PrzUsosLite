package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity
{
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> logIn());
    }

    private void logIn()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}