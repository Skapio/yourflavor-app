package com.example.yourflavor.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;

public class BasicAuthInterceptor implements Interceptor {
    private String credentials;

    public BasicAuthInterceptor(String email, String password) {
        this.updateCredentials(email, password);
    }

    @NotNull
    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        request = request
                .newBuilder()
                .header("Authorization", credentials)
                .build();

        return chain.proceed(request);
    }

    public void updateCredentials(String email, String password) {
        this.credentials = Credentials.basic(email, password);
    }
}
