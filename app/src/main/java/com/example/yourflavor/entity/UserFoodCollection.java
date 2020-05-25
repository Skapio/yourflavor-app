package com.example.yourflavor.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserFoodCollection {

    private Integer userFoodCollectionId;

    private String country;
    private String city;
    private String restaurantName;
    private String restaurantAddress;
    private Date date;
    private Integer rate;

    private Integer userId;
    private Integer appFoodCollectionId;

}
