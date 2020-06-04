package com.example.yourflavor.service;

import com.example.yourflavor.entity.UserFoodCollection;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PhotoService {
    @Multipart
    @POST("usr-coll/photo/{userFoodCollectionId}")
    Call<ResponseBody> sendPhoto(@Part MultipartBody.Part file, @Path("userFoodCollectionId") Integer userFoodCollectionId);

}
