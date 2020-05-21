package com.example.yourflavor.ui.myCollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yourflavor.R;
import com.example.yourflavor.ui.achievement.AchievementViewModel;
import com.example.yourflavor.ui.appCollection.AppCollectionViewModel;

public class MyCollectionFragment extends Fragment {

    private MyCollectionViewModel myCollectionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myCollectionViewModel =
                ViewModelProviders.of(this).get(MyCollectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mycollection, container, false);
        final TextView textView = root.findViewById(R.id.text_myCollection);
        myCollectionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
