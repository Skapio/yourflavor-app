package com.example.yourflavor.ui.myCollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.adapter.UserCollectionAdapter;
import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.request.AddUserFoodCollectionRequest;
import com.example.yourflavor.service.FavoriteService;
import com.example.yourflavor.service.UserFoodCollectionService;
import com.example.yourflavor.util.ApiHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyCollectionFragment extends Fragment {

    private MyCollectionViewModel myCollectionViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    EditText editCountry;
    EditText editCity;
    EditText editRestaurantName;
    EditText editRestaurantAddress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCollectionViewModel =
                ViewModelProviders.of(this).get(MyCollectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycollection, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerMy);
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
                .create(UserFoodCollectionService.class)
                .getUserFoodCollectionItems()
                .enqueue(new Callback<List<UserFoodCollection>>() {
                    @Override
                    public void onResponse(Call<List<UserFoodCollection>> call, Response<List<UserFoodCollection>> response) {
                        List<UserFoodCollection> userFoodCollections = response.body();


                        if (userFoodCollections != null) {
                            mAdapter = new UserCollectionAdapter(userFoodCollections, getContext(), id -> deleteMyCollection(id));
                            recyclerView.setAdapter(mAdapter);

                            Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse, size: " + userFoodCollections.size(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse appFoodCollections is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserFoodCollection>> call, Throwable throwable) {
                        Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse onFailure: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteMyCollection(Integer id) {
        Retrofit retrofit = ApiHelper.getRetrofit();

        retrofit
                .create(UserFoodCollectionService.class)
                .deleteMyCollection(id)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            connectAndGetApiData();
                            Toast.makeText(getContext(), "Deleted from collection", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Deleted filed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse onFailure: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
