package com.example.yourflavor.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourflavor.R;
import com.example.yourflavor.entity.UserFoodCollection;

import java.util.List;

public class UserCollectionAdapter extends RecyclerView.Adapter<UserCollectionAdapter.MyViewHolder> {

    private List<UserFoodCollection> mUserList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mCountry;
        public TextView mCity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCountry = itemView.findViewById(R.id.country);
            mCity = itemView.findViewById(R.id.city);
        }
    }

    public UserCollectionAdapter(List<UserFoodCollection> userList) { mUserList = userList; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_mycollection, parent, false);

        UserCollectionAdapter.MyViewHolder vh = new UserCollectionAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserFoodCollection currentItem = mUserList.get(position);

        holder.mCountry.setText(currentItem.getCountry());
        holder.mCity.setText(currentItem.getCity());
    }

    @Override
    public int getItemCount() { return mUserList.size(); }
}
