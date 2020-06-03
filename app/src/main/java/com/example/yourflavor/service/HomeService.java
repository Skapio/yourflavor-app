package com.example.yourflavor.service;

import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.request.AddUserFoodCollectionRequest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HomeService {
    @POST("usr-coll/test2")
    Call<UserFoodCollection> addUserFoodCollectionItems(@Body AddUserFoodCollectionRequest addUserFoodCollectionRequest);
}
