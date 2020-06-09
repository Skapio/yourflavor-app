package com.example.yourflavor.interfaces;

import com.example.yourflavor.request.UpdateUserFoodCollectionRequest;

public interface OnUpdateMyCollection {
    void onUpdate(Integer id, UpdateUserFoodCollectionRequest request);
}
