package com.example.yourflavor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.interfaces.OnDeleteFavorite;
import com.example.yourflavor.interfaces.OnShowRecipe;
import com.example.yourflavor.util.ApiHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private List<AppFoodCollection> mAppList;
    private Context context;
    private OnShowRecipe onShowRecipe;
    private OnDeleteFavorite onDeleteFavorite;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mName;
        public TextView mCuisine;
        public Button recipeButton;
        public Button deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cardviewFavorite);
            mName = itemView.findViewById(R.id.dishNameFav);
            mCuisine = itemView.findViewById(R.id.cuisineFav);
            recipeButton = itemView.findViewById(R.id.buttonRecipeFav);
            deleteButton = itemView.findViewById(R.id.buttonDeleteFav);
        }
    }

    public FavoriteAdapter(List<AppFoodCollection> appList, Context context, OnShowRecipe onShowRecipe, OnDeleteFavorite onDeleteFavorite) {
        mAppList = appList;
        this.context = context;
        this.onShowRecipe = onShowRecipe;
        this.onDeleteFavorite = onDeleteFavorite;
    }

    @NonNull
    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_favorite, parent, false);

        FavoriteAdapter.MyViewHolder vh = new FavoriteAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.MyViewHolder holder, int position) {
        AppFoodCollection currentItem = mAppList.get(position);

        //Loading image using Picasso
        String imageUrl = createImageUrl(currentItem);
        Picasso picasso = ApiHelper.getPicasso(context);
        picasso.load(imageUrl).into(holder.mImageView);

        //holder.mImageView.setImageResource();
        holder.mName.setText(currentItem.getDishName());
        holder.mCuisine.setText(currentItem.getCuisineType());

        holder.recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowRecipe.onShow(currentItem.getRecipe());
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteFavorite.onDelete(currentItem.getAppFoodCollectionId());
            }
        });
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
