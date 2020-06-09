package com.example.yourflavor.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.yourflavor.interfaces.OnUpdateMyCollection;
import com.example.yourflavor.request.AddUserFoodCollectionRequest;
import com.example.yourflavor.util.ApiHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserCollectionAdapter extends RecyclerView.Adapter<UserCollectionAdapter.MyViewHolder> {

    private List<UserFoodCollection> mUserList;
    private Context context;
    private OnDeleteMyCollection onDeleteMyCollection;
    private OnUpdateMyCollection onUpdateMyCollection;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mCountry;
        public TextView mCity;
        public TextView mRestaurantName;
        public TextView mRestaurantAddress;
        public TextView mDate;
        public EditText editCountry;
        public EditText editCity;
        public EditText editRestaurantName;
        public EditText editRestaurantAddress;
        public Button deleteButton;
        public Button updateButton;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cardviewFoodcollectionImage);
            mCountry = itemView.findViewById(R.id.country);
            mCity = itemView.findViewById(R.id.city);
            mRestaurantName = itemView.findViewById(R.id.restaurantNameMy);
            mRestaurantAddress = itemView.findViewById(R.id.restaurantAddressMy);
            mDate = itemView.findViewById(R.id.dateMy);

            deleteButton = itemView.findViewById(R.id.buttonDeleteMy);
            updateButton = itemView.findViewById(R.id.buttonUpdate);

            editCountry = (EditText) itemView.findViewById(R.id.editCountry);
            editCity = (EditText) itemView.findViewById(R.id.editCity);
            editRestaurantName = (EditText) itemView.findViewById(R.id.editRestaurantNameMy);
            editRestaurantAddress = (EditText) itemView.findViewById(R.id.editRestaurantAddressMy);

            cardView = itemView.findViewById(R.id.cardviewFoodcollection);

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    mCountry.setVisibility(View.GONE);
                    editCountry = itemView.findViewById(R.id.editCountry);
                    editCountry.setVisibility(View.VISIBLE);

                    mCity.setVisibility(View.GONE);
                    editCity = itemView.findViewById(R.id.editCity);
                    editCity.setVisibility(View.VISIBLE);

                    mRestaurantName.setVisibility(View.GONE);
                    editRestaurantName = itemView.findViewById(R.id.editRestaurantNameMy);
                    editRestaurantName.setVisibility(View.VISIBLE);

                    mRestaurantAddress.setVisibility(View.GONE);
                    editRestaurantAddress = itemView.findViewById(R.id.editRestaurantAddressMy);
                    editRestaurantAddress.setVisibility(View.VISIBLE);

                    return false;
                }
            });

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

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sEditCountry = holder.editCountry.getText().toString();
                String sEditCity = holder.editCity.getText().toString();
                String sEditRestaurantName = holder.editRestaurantName.getText().toString();
                String sEditRestaurantAddress = holder.editRestaurantAddress.getText().toString();

                if(!sEditCountry.matches("") && !sEditCity.matches("") && !sEditRestaurantName.matches("") && !sEditRestaurantAddress.matches(""))
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Are you sure?");
                    builder.setMessage("Do you wanna update the item?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context,
                                    "Success", Toast.LENGTH_SHORT).show();

                            onUpdateMyCollection.onUpdate(currentItem.getUserFoodCollectionId());
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
                else {
                    Toast.makeText(context,
                            "Can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
