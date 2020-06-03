package com.example.yourflavor.service;

import com.example.yourflavor.entity.UserFoodCollection;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserFoodCollectionService {
    @GET("usr-coll/list")
    Call<List<UserFoodCollection>> getUserFoodCollectionItems();

}
