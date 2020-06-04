package com.example.yourflavor.service;

import com.example.yourflavor.entity.AppFoodCollection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FavoriteService {
    @GET("favorite")
    Call<List<AppFoodCollection>> getFavoriteItems();
}
