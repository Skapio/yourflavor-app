package com.example.yourflavor.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static BasicAuthInterceptor basicAuthInterceptor;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

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
                .baseUrl("http://192.168.1.124:8081")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
