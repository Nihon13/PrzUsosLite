package com.nihon.przusoslite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Scanner;

public class UsosWebAuthorization extends AppCompatActivity
{
    public static final String PIN_CODE = "com.nihon.przusoslite.PIN_CODE";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usos_web_authorization);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        String url = intent.getStringExtra(SplashScreen.USOS_URL_AUTH);

        WebView webView = findViewById(R.id.usos_wv);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(true);

        webView.loadUrl(url);
    }

    public class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            final String url = request.getUrl().toString();

            if (url.contains("nonexistingusospage/callback") && url.contains("oauth_verifier="))
            {
                String pinCode = "";
                try (Scanner scanner = new Scanner(url))
                {
                    scanner.useDelimiter("&");

                    while (scanner.hasNext())
                    {
                        pinCode = scanner.next();

                        if (pinCode.contains("oauth_verifier="))
                        {
                            pinCode = pinCode.replace("oauth_verifier=", "");
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(PIN_CODE, pinCode);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }
                    }
                }

                return true;
            }

            return false;
        }
    }
}