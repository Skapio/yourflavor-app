package com.example.yourflavor.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.adapter.AppCollectionAdapter;
import com.example.yourflavor.adapter.FavoriteAdapter;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.service.AppFoodCollectionService;
import com.example.yourflavor.service.FavoriteService;
import com.example.yourflavor.ui.appCollection.RecipeDialog;
import com.example.yourflavor.util.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerFav);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        connectAndGetApiData();

        return root;
    }

    public void openDialog(String recipe) {
        RecipeDialog recipeDialog = new RecipeDialog(recipe);
        recipeDialog.show(getActivity().getSupportFragmentManager(), "recipe dialog");
    }


    private void connectAndGetApiData() {
        Retrofit retrofit = ApiHelper.getRetrofit();

        retrofit
                .create(FavoriteService.class)
                .getFavoriteItems()
                .enqueue(new Callback<List<AppFoodCollection>>() {
                    @Override
                    public void onResponse(Call<List<AppFoodCollection>> call, Response<List<AppFoodCollection>> response) {
                        List<AppFoodCollection> appFoodCollections = response.body();

                        if (appFoodCollections != null) {
                            mAdapter = new FavoriteAdapter(appFoodCollections, getContext(), recipe -> openDialog(recipe));
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse appFoodCollections is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AppFoodCollection>> call, Throwable throwable) {
                        Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse onFailure: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
