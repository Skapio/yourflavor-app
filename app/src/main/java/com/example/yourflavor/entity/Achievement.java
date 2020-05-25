package com.example.yourflavor.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Achievement {

    private Integer achievementId;
    private String name;
    private String description;

}
