package com.example.yourflavor.service;

import com.example.yourflavor.entity.UserFoodCollection;

import retrofit2.Call;
import retrofit2.http.POST;

public interface PhotoService {
    @POST("/photo/{userFoodCollectionId}")
    Call<Void> getPhoto();
}
