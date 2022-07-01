package com.nihon.przusoslite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity
{
    public static final String USOS_URL_AUTH = "com.nihon.przusoslite.USOS_URL_AUTH";
    public static final String PIN_CODE = "com.nihon.przusoslite.PIN_CODE";
    private final int REQUEST_CODE_PIN_CODE = 1313;

    private OAuth10aService service;
    private OAuth1RequestToken requestToken;
    private OAuth1AccessToken accessToken;
    String pinCode;

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

        class GetRequestToken extends AsyncTask<Void, Void, String>
        {
            @Override
            protected String doInBackground(Void... voids) {
                service = OAuthServiceSingleton.getInstance(BuildConfig.USOS_API_KEY, BuildConfig.USOS_API_SECRET, "https://nonexistingusospage/callback").getService();

                try {
                    requestToken = service.getRequestToken();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return requestToken.getTokenSecret();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                String authUrl = service.getAuthorizationUrl(requestToken);
                openUsosAuthPage(authUrl);
            }
        }

        button.setOnClickListener(v ->
        {
            GetRequestToken getToken = new GetRequestToken();
            getToken.execute();
        });
    }

    class AuthorizeToken extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            try
            {
                accessToken = service.getAccessToken(requestToken, pinCode);
                OAuthServiceSingleton.getInstance().setAccessToken(accessToken);
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            } catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            super.onPostExecute(unused);

            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PIN_CODE && resultCode == RESULT_OK)
        {
            assert data != null;
            pinCode = data.getStringExtra(PIN_CODE);

            AuthorizeToken authorizeToken = new AuthorizeToken();
            authorizeToken.execute();
        }
    }

    private void openUsosAuthPage(String url)
    {
        Intent intent = new Intent(this, UsosWebAuthorization.class);
        intent.putExtra(USOS_URL_AUTH, url);
        startActivityForResult(intent, REQUEST_CODE_PIN_CODE);
    }
}