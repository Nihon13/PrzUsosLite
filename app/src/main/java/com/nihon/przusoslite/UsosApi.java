package com.nihon.przusoslite;

import com.github.scribejava.core.builder.api.DefaultApi10a;

public class UsosApi extends DefaultApi10a
{
    private static UsosApi mInstance;

    private UsosApi() {}

    public static UsosApi getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UsosApi();
        }

        return mInstance;
    }

    @Override
    public String getRequestTokenEndpoint()
    {
        return "https://usosapps.prz.edu.pl/services/oauth/request_token?scopes=studies|grades|photo";
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://usosapps.prz.edu.pl/services/oauth/access_token";
    }

    @Override
    protected String getAuthorizationBaseUrl()
    {
        return "https://usosapps.prz.edu.pl/services/oauth/authorize";
    }
}