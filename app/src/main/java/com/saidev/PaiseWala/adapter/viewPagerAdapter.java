package com.saidev.PaiseWala.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.viewmodel.viewPagerModel;

import java.util.ArrayList;

public class viewPagerAdapter extends RecyclerView.Adapter<viewPagerAdapter.ViewHolder> {

    ArrayList<viewPagerModel> viewPagerModelArrayList;

    public viewPagerAdapter(ArrayList<viewPagerModel> viewPagerModelArrayList) {
        this.viewPagerModelArrayList = viewPagerModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usepageitem, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        viewPagerModel viewPagerModelclass = viewPagerModelArrayList.get(position);
        holder.imageSteps.setImageResource(viewPagerModelclass.getImageId());
        holder.stepNumber.setText(viewPagerModelclass.getStepNo());
        holder.stepDescription.setText(viewPagerModelclass.getMainStep());

    }

    @Override
    public int getItemCount() {
        return viewPagerModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageSteps;
        TextView stepNumber, stepDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSteps = itemView.findViewById(R.id.stepsImage);
            stepNumber = itemView.findViewById(R.id.stepsCount);
            stepDescription = itemView.findViewById(R.id.stepsBody);

        }
    }
}
