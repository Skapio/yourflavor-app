package com.example.yourflavor.service;

import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.request.AddUserFoodCollectionRequest;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserFoodCollectionService {
    @GET("usr-coll/list")
    Call<List<UserFoodCollection>> getUserFoodCollectionItems();

    @DELETE("usr-coll/delete/{id}")
    Call<Void> deleteMyCollection(@Path("id") Integer id);

    @POST
    Call<UserFoodCollection> updateUserFoodCollectionItems(@Body AddUserFoodCollectionRequest addUserFoodCollectionRequest);
}
