package com.example.yourflavor.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.Achievement;
import com.example.yourflavor.entity.Achievements;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.entity.UserAchievement;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.MyViewHolder> {

    private Achievements achievements;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mAchievementName;
        public TextView mDescription;
        public RelativeLayout achievement;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mAchievementName = itemView.findViewById(R.id.achievementName);
            mDescription = itemView.findViewById(R.id.description);
            achievement = itemView.findViewById(R.id.relativeAchievement);
        }
    }

    public AchievementAdapter(Achievements achievements) { this.achievements = achievements; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_achievement, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    private UserAchievement findUserAchievement(List<UserAchievement> achievements, Integer id) {
        for (UserAchievement userAchievement : achievements) {
            if (userAchievement.getAchievementId().equals(id)) {
                return userAchievement;
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Achievement currentItem = achievements.getAchievements().get(position);
        UserAchievement userAchievement = findUserAchievement(achievements.getUserAchievements(), currentItem.getAchievementId());

        if (userAchievement != null) {
            holder.achievement.setBackgroundColor(Color.GREEN);
        } else {
            holder.achievement.setBackgroundColor(Color.GRAY);
        }

        // holder.mImageAchievement.setImageResource();
        holder.mAchievementName.setText(currentItem.getName());
        holder.mDescription.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() { return achievements.getAchievements().size(); }
}
