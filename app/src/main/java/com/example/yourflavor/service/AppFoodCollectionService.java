package com.example.yourflavor.service;

import com.example.yourflavor.entity.AppFoodCollection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppFoodCollectionService {
    @GET("app/test2")
    Call<List<AppFoodCollection>> getAppFoodCollectionItems();

}
