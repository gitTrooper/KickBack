package com.saidev.PaiseWala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.model.payoutHistoryModel;

import java.util.ArrayList;

public class payoutHistoryAdapter extends RecyclerView.Adapter<payoutHistoryAdapter.payoutViewHolder> {


    Context context;
    ArrayList<payoutHistoryModel> historyModels;

    public payoutHistoryAdapter(Context context, ArrayList<payoutHistoryModel> historyModels) {
        this.context = context;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public payoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.payout_history_holder, parent, false);
        return new payoutViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull payoutViewHolder holder, int position) {
        payoutHistoryModel model = historyModels.get(position);
        holder.amount.setText("₹"+model.getWithdrawalAmount());
        if (model.getWithdrawStatus()==0){
            holder.amountStatus.setText("Pending");
        }else {
            holder.amountStatus.setText("Confirmed");
        }
        if (model.getWithdrawStatus()==0){
            holder.amountStatus.setBackgroundResource(R.drawable.pending_bg);
        }
        else {
            holder.amountStatus.setBackgroundResource(R.drawable.pending_bg2);
        }
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public static class payoutViewHolder extends RecyclerView.ViewHolder {

        TextView amount, amountStatus;

        public payoutViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.paymentAmount);
            amountStatus = itemView.findViewById(R.id.paymentStatus);

        }
    }
}
