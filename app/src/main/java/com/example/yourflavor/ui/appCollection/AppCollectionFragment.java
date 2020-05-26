package com.example.yourflavor.ui.appCollection;

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

import com.example.yourflavor.R;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.service.AppFoodCollectionService;
import com.example.yourflavor.util.BasicAuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppCollectionFragment extends Fragment {
    private static Retrofit retrofit = null;


    private AppCollectionViewModel appCollectionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appCollectionViewModel =
                ViewModelProviders.of(this).get(AppCollectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appcollection, container, false);
        final TextView textView = root.findViewById(R.id.text_appCollection);
        appCollectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        connectAndGetApiData();
        return root;
    }

    private void connectAndGetApiData() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor("first@example.com", "12345"))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.227:8081")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        AppFoodCollectionService appFoodCollectionService = retrofit.create(AppFoodCollectionService.class);
        Call<List<AppFoodCollection>> call = appFoodCollectionService.getAppFoodCollectionItems();

        call.enqueue(new Callback<List<AppFoodCollection>>() {
            @Override
            public void onResponse(Call<List<AppFoodCollection>> call, Response<List<AppFoodCollection>> response) {
                List<AppFoodCollection> appFoodCollections = response.body();

                if (appFoodCollections != null) {
                    // Tutaj trzeba utworzyć "widok" elementów w aplikacji
                    Toast.makeText(getContext(), "AppCollectionFragment connectAndGetApiData onResponse, size: " + appFoodCollections.size(), Toast.LENGTH_SHORT).show();
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
