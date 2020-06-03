package com.example.yourflavor.util;

import android.content.Context;

import com.example.yourflavor.service.HomeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static BasicAuthInterceptor basicAuthInterceptor;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
//    private static String host = "http://192.168.0.227:8081/";
//    private static String host = "http://192.168.1.124:8081/";
      private static String host = "http://192.168.0.136:8081/";

    public static synchronized Picasso getPicasso(Context context) {
        return new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    public static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            init("", "");
        }

        return retrofit;
    }

    public static synchronized Retrofit getRetrofit(String email, String password) {
        if (retrofit == null) {
            init(email, password);
        } else {
            basicAuthInterceptor.updateCredentials(email, password);
        }

        return retrofit;
    }

    private static void init(String email, String password) {
        basicAuthInterceptor = new BasicAuthInterceptor(email, password);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(basicAuthInterceptor)
                .cache(null)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getBasePath())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static String getBasePath() {
        return host;
    }

    public static Retrofit getClient(String host)
    {

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(host)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static HomeService getHomeService()
    {
        return ApiHelper.getClient(host).create(HomeService.class);
    }
}
