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
import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.util.ApiHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserCollectionAdapter extends RecyclerView.Adapter<UserCollectionAdapter.MyViewHolder> {

    private List<UserFoodCollection> mUserList;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mCountry;
        public TextView mCity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cardviewFoodcollectionImage);
            mCountry = itemView.findViewById(R.id.country);
            mCity = itemView.findViewById(R.id.city);
        }
    }

    public UserCollectionAdapter(List<UserFoodCollection> userList, Context context) {
        mUserList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_mycollection, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserFoodCollection currentItem = mUserList.get(position);


        //Loading image using Picasso
        String imageUrl = createImageUrl(currentItem);
        Picasso picasso = ApiHelper.getPicasso(context);
        picasso.load(imageUrl).into(holder.mImageView);

        holder.mCountry.setText(currentItem.getCountry());
        holder.mCity.setText(currentItem.getCity());
    }

    @Override
    public int getItemCount() { return mUserList.size(); }


    private String createImageUrl(UserFoodCollection userFoodCollection) {
        if (userFoodCollection.getPhotos().size() == 0) {
            // Można tutaj zwrócić link do jakiegoś dowolnego domyślnego zdjęcia
            return "https://via.placeholder.com/50";
        }

        String photo = userFoodCollection.getPhotos().get(0);

        return ApiHelper.getBasePath() + "usr-coll/photo/" + userFoodCollection.getUserFoodCollectionId() + "/" + photo;
    }
}
