package com.example.yourflavor.ui.achievement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.adapter.AchievementAdapter;
import com.example.yourflavor.entity.Achievement;
import com.example.yourflavor.entity.Achievements;
import com.example.yourflavor.service.AchievementService;
import com.example.yourflavor.util.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AchievementFragment extends Fragment {

    private AchievementViewModel achievementViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementViewModel =
                ViewModelProviders.of(this).get(AchievementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievement, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerAchievement);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)


        connectAndGetApiData();
        return root;
    }

    private void connectAndGetApiData() {
        Retrofit retrofit = ApiHelper.getRetrofit();

        retrofit
                .create(AchievementService.class)
                .getAchievementItems()
                .enqueue(new Callback<Achievements>() {
                    @Override
                    public void onResponse(Call<Achievements> call, Response<Achievements> response) {
                        Achievements achievements = response.body();


                        if (achievements != null) {
                            mAdapter = new AchievementAdapter(achievements);
                            recyclerView.setAdapter(mAdapter);

                        } else {
                            Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse appFoodCollections is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Achievements> call, Throwable throwable) {
                        Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse onFailure: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
