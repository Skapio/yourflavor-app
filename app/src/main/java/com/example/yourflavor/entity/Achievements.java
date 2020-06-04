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
public class Achievements {
    private List<Achievement> achievements;
    private List<UserAchievement> userAchievements;
}
