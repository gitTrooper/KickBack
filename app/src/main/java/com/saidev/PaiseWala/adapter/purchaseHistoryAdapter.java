package com.saidev.PaiseWala.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.model.purchaseHistoyModel;

import java.util.ArrayList;

public class purchaseHistoryAdapter extends RecyclerView.Adapter<purchaseHistoryAdapter.MyViewHolder> {

    String color;
    Context ctx;
    ArrayList<purchaseHistoyModel> histoyModelArrayList;

    public purchaseHistoryAdapter(Context ctx, ArrayList<purchaseHistoyModel> histoyModelArrayList) {
        this.ctx = ctx;
        this.histoyModelArrayList = histoyModelArrayList;
    }

    @NonNull
    @Override
    public purchaseHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(ctx).inflate(R.layout.history_holder, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull purchaseHistoryAdapter.MyViewHolder holder, int position) {

        purchaseHistoyModel model = histoyModelArrayList.get(position);
        holder.purchaseTitle.setText(model.getStatus());
        holder.cashStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.purchaseTitle.setText("Great! We will validate your purchase and reward you");
                holder.cashStatus.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return histoyModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        Button cashStatus;
        TextView purchaseTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseTitle = itemView.findViewById(R.id.productHistoryTitle);
            cashStatus = itemView.findViewById(R.id.cashbackStatus);
        }
    }

}
