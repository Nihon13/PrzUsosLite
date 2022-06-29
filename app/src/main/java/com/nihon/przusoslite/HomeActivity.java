package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity
{
    ImageView imageView;
    CardView scheduleCV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        imageView = findViewById(R.id.photo);
        scheduleCV = findViewById(R.id.scheduleCard);

        scheduleCV.setOnClickListener(v -> goToSchedule());

        String url = "";

        if (!url.equals(""))
        {
            Picasso.get().load(url).placeholder(R.drawable.ic_baseline_face_24).resize(100,100).centerCrop().into(imageView);
        }
    }

    private void goToSchedule()
    {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}