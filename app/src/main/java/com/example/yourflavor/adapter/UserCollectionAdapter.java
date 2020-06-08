package com.example.yourflavor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.AppFoodCollection;
import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.interfaces.OnDeleteFavorite;
import com.example.yourflavor.interfaces.OnDeleteMyCollection;
import com.example.yourflavor.util.ApiHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserCollectionAdapter extends RecyclerView.Adapter<UserCollectionAdapter.MyViewHolder> {

    private List<UserFoodCollection> mUserList;
    private Context context;
    private OnDeleteMyCollection onDeleteMyCollection;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mCountry;
        public TextView mCity;
        public TextView mRestaurantName;
        public TextView mRestaurantAddress;
        public TextView mDate;
        public Button deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cardviewFoodcollectionImage);
            mCountry = itemView.findViewById(R.id.country);
            mCity = itemView.findViewById(R.id.city);
            mRestaurantName = itemView.findViewById(R.id.restaurantNameMy);
            mRestaurantAddress = itemView.findViewById(R.id.restaurantAddressMy);
            mDate = itemView.findViewById(R.id.dateMy);
            deleteButton = itemView.findViewById(R.id.buttonDeleteMy);
        }
    }

    public UserCollectionAdapter(List<UserFoodCollection> userList, Context context, OnDeleteMyCollection onDeleteMyCollection) {
        mUserList = userList;
        this.context = context;
        this.onDeleteMyCollection = onDeleteMyCollection;
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


        SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy"); // Set your date format
        String currentData = sdf.format(currentItem.getDate()); // Get Date String according to date format

        //Loading image using Picasso
        String imageUrl = createImageUrl(currentItem);
        Picasso picasso = ApiHelper.getPicasso(context);
        picasso.load(imageUrl).into(holder.mImageView);

        holder.mCountry.setText(currentItem.getCountry());
        holder.mCity.setText(currentItem.getCity());
        holder.mRestaurantName.setText(currentItem.getRestaurantName());
        holder.mRestaurantAddress.setText(currentItem.getRestaurantAddress());
        holder.mDate.setText(currentData);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Are you sure?");
                builder.setMessage("Do you wanna delete the item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteMyCollection.onDelete(currentItem.getUserFoodCollectionId());
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,
                                "No Button Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
