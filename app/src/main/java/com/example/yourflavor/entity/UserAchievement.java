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
public class UserAchievement {

    private Integer userAchievementId;
    private Achievement achievement;
    private Integer userId;
    private Date date;

}
