package com.example.yourflavor.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginService {
    @GET("login")
    Call<Void> login();
}
