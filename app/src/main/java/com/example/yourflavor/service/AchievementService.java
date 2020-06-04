package com.example.yourflavor.service;

import com.example.yourflavor.entity.Achievement;
import com.example.yourflavor.entity.Achievements;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AchievementService {
    @GET("achi/list")
    Call <Achievements> getAchievementItems();
}
