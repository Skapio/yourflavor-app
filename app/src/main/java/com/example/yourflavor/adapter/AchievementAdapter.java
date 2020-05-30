package com.example.yourflavor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.Achievement;
import com.example.yourflavor.entity.AppFoodCollection;

import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.MyViewHolder> {

    private List<Achievement> mAchievementList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // public ImageView mImageAchievement;
        public TextView mAchievementName;
        public TextView mDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // mImageAchievement = itemView.findViewById(R.id.imageAchievement);
            mAchievementName = itemView.findViewById(R.id.achievementName);
            mDescription = itemView.findViewById(R.id.description);
        }
    }

    public AchievementAdapter(List<Achievement> achievementList) { mAchievementList = achievementList; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_achievement, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Achievement currentItem = mAchievementList.get(position);

        // holder.mImageAchievement.setImageResource();
        holder.mAchievementName.setText(currentItem.getName());
        holder.mDescription.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() { return mAchievementList.size(); }
}
