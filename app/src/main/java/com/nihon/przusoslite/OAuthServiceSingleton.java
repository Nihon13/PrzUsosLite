package com.nihon.przusoslite;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.oauth.OAuth10aService;

public class OAuthServiceSingleton
{
    private static OAuthServiceSingleton mInstance;
    private OAuth10aService mService;
    private static OAuth1AccessToken mAccessToken;

    private OAuthServiceSingleton(String apiKey, String apiSecret, String callback)
    {
        mService = new ServiceBuilder(apiKey).apiSecret(apiSecret).callback(callback).build(UsosApi.getInstance());
    }

    public static synchronized OAuthServiceSingleton getInstance(String apiKey, String apiSecret, String callback)
    {
        if (mInstance == null)
        {
            mInstance = new OAuthServiceSingleton(apiKey, apiSecret, callback);
        }

        return mInstance;
    }

    public static synchronized OAuthServiceSingleton getInstance()
    {
        if (mInstance == null)
        {
            return null;
        }

        return mInstance;
    }

    public void setAccessToken(OAuth1AccessToken token)
    {
        mAccessToken = token;
    }

    public OAuth10aService getService()
    {
        return mService;
    }
    public OAuth1AccessToken getAccessToken() { return mAccessToken; }
}