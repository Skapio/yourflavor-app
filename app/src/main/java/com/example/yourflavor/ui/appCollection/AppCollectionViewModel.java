package com.example.yourflavor.ui.appCollection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppCollectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AppCollectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AppCollection fragment");
    }

    public LiveData<String> getText() { return mText; }
}
