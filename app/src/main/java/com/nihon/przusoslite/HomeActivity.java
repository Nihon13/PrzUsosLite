package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity
{
    private OAuth10aService service;
    private OAuth1AccessToken accessToken;

    private ImageView imageView;
    private CardView scheduleCV;
    private CardView gradesCV;
    private TextView tvName;

    private JSONObject JSONStudentData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        service = OAuthServiceSingleton.getInstance().getService();
        accessToken = OAuthServiceSingleton.getInstance().getAccessToken();

        imageView = findViewById(R.id.photo);
        scheduleCV = findViewById(R.id.scheduleCard);
        gradesCV = findViewById(R.id.gradesCard);
        tvName = findViewById(R.id.name);

        String url = "";

        if (!url.equals(""))
        {
            Picasso.get().load(url).placeholder(R.drawable.ic_baseline_face_24).resize(100,100).centerCrop().into(imageView);
        }

        scheduleCV.setOnClickListener(v -> goToSchedule());
        gradesCV.setOnClickListener(v -> goToGrades());

        class LoadStudentData extends AsyncTask<Void, Void, Void>
        {
            Response response = null;

            @Override
            protected Void doInBackground(Void... voids)
            {
                final OAuthRequest request = new OAuthRequest(Verb.GET, "https://usosapps.prz.edu.pl/services/users/user?fields=first_name|last_name|photo_urls[100x100]");
                service.signRequest(accessToken, request);

                try
                {
                    response = service.execute(request);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                } catch (ExecutionException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void unused)
            {
                super.onPostExecute(unused);
                try
                {
                    JSONStudentData = new JSONObject(response.getBody());

                    JSONObject obj = JSONStudentData.getJSONObject("photo_urls");
                    String url = obj.getString("100x100");

                    if (!url.equals(""))
                    {
                        Picasso.get().load(url).placeholder(R.drawable.ic_baseline_face_24).resize(100,100).centerCrop().into(imageView);
                    }

                    String name = JSONStudentData.getString("first_name") + " " + JSONStudentData.getString("last_name");

                    tvName.setText(name);
                } catch (IOException | JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

        LoadStudentData loadStudentData = new LoadStudentData();
        loadStudentData.execute();
    }

    @Override
    public void onBackPressed()
    {
        return;
    }

    private void goToSchedule()
    {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    private void goToGrades()
    {
        Intent intent = new Intent(this, GradesActivity.class);
        startActivity(intent);
    }
}