package com.example.yourflavor.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppFoodCollection {

    private Integer appFoodCollectionId;
    private String dishName;
    private String cuisineType;
    private String recipe;
    private List<String> photos;
}
