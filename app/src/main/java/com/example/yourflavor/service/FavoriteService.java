package com.example.yourflavor.service;

import com.example.yourflavor.entity.AppFoodCollection;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FavoriteService {
    @GET("favorite")
    Call<List<AppFoodCollection>> getFavoriteItems();

    @POST("favorite/{id}")
    Call<Void> addFavorite(@Path("id") Integer id);
}
