package com.example.yourflavor.ui.myCollection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCollectionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCollectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyCollection fragment");
    }

    public LiveData<String> getText() { return mText; }
}
