package com.example.yourflavor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.AppFoodCollection;

import java.util.ArrayList;

public class AppCollectionAdapter extends RecyclerView.Adapter<AppCollectionAdapter.MyViewHolder> {

    private ArrayList<AppFoodCollection> mAppList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //public ImageView mImageView;
        public TextView mName;
        public TextView mCuisine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.imageView);
            mName = itemView.findViewById(R.id.name);
            mCuisine = itemView.findViewById(R.id.cuisine);
        }
    }

    public AppCollectionAdapter(ArrayList<AppFoodCollection> appList) {
        mAppList = appList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_appcollection, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppFoodCollection currentItem = mAppList.get(position);

        //holder.mImageView.setImageResource();
        holder.mName.setText(currentItem.getDishName());
        holder.mCuisine.setText(currentItem.getCuisineType());
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}
