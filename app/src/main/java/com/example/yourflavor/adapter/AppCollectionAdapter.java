package com.example.yourflavor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.util.ApiHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppCollectionAdapter extends RecyclerView.Adapter<AppCollectionAdapter.MyViewHolder> {
    private List<AppFoodCollection> mAppList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mName;
        public TextView mCuisine;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cardviewAppcollectionImage);
            mName = itemView.findViewById(R.id.dishName);
            mCuisine = itemView.findViewById(R.id.cuisine);
        }
    }

    public AppCollectionAdapter(List<AppFoodCollection> appList, Context context) {
        mAppList = appList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_appcollection, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppFoodCollection currentItem = mAppList.get(position);

        //Loading image using Picasso
        String imageUrl = createImageUrl(currentItem);
        Picasso picasso = ApiHelper.getPicasso(context);
        picasso.load(imageUrl).into(holder.mImageView);

        //holder.mImageView.setImageResource();
        holder.mName.setText(currentItem.getDishName());
        holder.mCuisine.setText(currentItem.getCuisineType());
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    private String createImageUrl(AppFoodCollection appFoodCollection) {
        if (appFoodCollection.getPhotos().size() == 0) {
            // Można tutaj zwrócić link do jakiegoś dowolnego domyślnego zdjęcia
            return "https://via.placeholder.com/50";
        }

        String photo = appFoodCollection.getPhotos().get(0);

        return ApiHelper.getBasePath() + "app/photo/" + appFoodCollection.getAppFoodCollectionId() + "/" + photo;
    }
}
