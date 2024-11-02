package com.saidev.PaiseWala.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.model.storeModel;
import com.saidev.PaiseWala.storeDetailsPage;

import java.util.ArrayList;

public class storeAdapter extends RecyclerView.Adapter<storeAdapter.storeViewHolder> {

     ArrayList<storeModel> storeModelArrayList;
     Context context;

    public storeAdapter(Context context, ArrayList<storeModel> storeModelArrayList) {
        this.context = context;
        this.storeModelArrayList = storeModelArrayList;
    }

    @NonNull
    @Override
    public storeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.storecard, parent, false);
        return new storeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull storeViewHolder holder, int position) {
        storeModel model = storeModelArrayList.get(position);
        Glide.with(holder.itemView.getContext()).load(model.getStoreImgLink()).into(holder.StoreImageView);
        holder.StoreNameView.setText(model.getStoreName());
        holder.storeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), storeDetailsPage.class);
                i.putExtra("storeName", model.getStoreName());
                i.putExtra("storeAffiliateLink", model.getStoreLink());
                view.getContext().startActivity(i);
            }
        });
        holder.card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.recycler_anim));
    }

    @Override
    public int getItemCount() {
        return storeModelArrayList.size();
    }

    public static class storeViewHolder extends RecyclerView.ViewHolder{

        ImageView StoreImageView;
        TextView StoreNameView;
        ConstraintLayout storeCard;
        CardView card;

        public storeViewHolder(@NonNull View itemView) {
            super(itemView);
            StoreImageView = itemView.findViewById(R.id.storeImage);
            StoreNameView = itemView.findViewById(R.id.storeName);
            storeCard = itemView.findViewById(R.id.storeCardClick);
            card = itemView.findViewById(R.id.cardView);
        }
    }

}
