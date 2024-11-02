package com.saidev.PaiseWala.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.model.budget_model;
import com.saidev.PaiseWala.onclick_productView;
import com.saidev.PaiseWala.storeDetailsPage;

import java.util.ArrayList;

public class budgetAdapter extends RecyclerView.Adapter {

    Context ctx;
    ArrayList<budget_model> modelArrayList;

    public budgetAdapter(Context ctx, ArrayList<budget_model> modelArrayList) {
        this.ctx = ctx;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        budget_model model = modelArrayList.get(position);
        return model.getViewType();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        if (viewType==1){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.animated_ad_banner, parent, false);
            return new bannerViewHolder(v);
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.animated_ad_poster, parent, false);
        return new posterViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        budget_model model = modelArrayList.get(position);
        if (this.getItemViewType(position)==1){
            bannerViewHolder holder1 = (bannerViewHolder) holder;
            Glide.with(holder.itemView.getContext()).load(model.getImgLink()).into(holder1.firstBannerImage);
            holder1.firstBannerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), onclick_productView.class);
                    i.putExtra("url", model.getStoreLink());
                    view.getContext().startActivity(i);
                }
            });
        }
        if (this.getItemViewType(position)==2){
            posterViewHolder holder2 = (posterViewHolder) holder;
            Glide.with(holder.itemView.getContext()).load(model.getImgLink()).into(holder2.posterImage);
            holder2.posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), onclick_productView.class);
                    i.putExtra("url", model.getStoreLink());
                    view.getContext().startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public static class bannerViewHolder extends RecyclerView.ViewHolder{

        ImageView firstBannerImage;

        public bannerViewHolder(@NonNull View itemView) {
            super(itemView);
            firstBannerImage = itemView.findViewById(R.id.first_banner);
        }
    }

    public static class posterViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImage;

        public posterViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.first_poster);
        }
    }


}
