package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
{
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        imageView = findViewById(R.id.photo);

        String url = "";

        if (!url.equals(""))
        {
            Picasso.get().load(url).placeholder(R.drawable.ic_baseline_face_24).resize(100,100).centerCrop().into(imageView);
        }
    }
}