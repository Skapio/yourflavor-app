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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.adapter.AppCollectionAdapter;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.service.AppFoodCollectionService;
import com.example.yourflavor.util.RetrofitHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppCollectionFragment extends Fragment {
    private AppCollectionViewModel appCollectionViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerApp);
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
        Retrofit retrofit = RetrofitHelper.getRetrofit();

        retrofit
                .create(AppFoodCollectionService.class)
                .getAppFoodCollectionItems()
                .enqueue(new Callback<List<AppFoodCollection>>() {
                    @Override
                    public void onResponse(Call<List<AppFoodCollection>> call, Response<List<AppFoodCollection>> response) {
                        List<AppFoodCollection> appFoodCollections = response.body();


                        if (appFoodCollections != null) {
                            mAdapter = new AppCollectionAdapter(appFoodCollections);
                            recyclerView.setAdapter(mAdapter);

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
