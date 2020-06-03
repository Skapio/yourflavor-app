package com.example.yourflavor.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddUserFoodCollectionRequest {

    private String country;
    private String city;
    private String restaurantName;
    private String restaurantAddress;
    private Integer rate;
    private Integer appFoodCollectionId;

}
