package com.saidev.PaiseWala.adapter;


import android.content.Intent;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.saidev.PaiseWala.R;
import com.saidev.PaiseWala.model.storeModel;
import com.saidev.PaiseWala.storeDetailsPage;

public class allStoreAdapter extends FirebaseRecyclerAdapter<storeModel, allStoreAdapter.allStoreViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public allStoreAdapter(@NonNull FirebaseRecyclerOptions<storeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull allStoreViewHolder holder, int position, @NonNull storeModel model) {
            holder.allStoresName.setText(model.getStoreName());
            Glide.with(holder.itemView.getContext()).load(model.getStoreImgLink()).into(holder.allStoresImage);
            holder.constLayout.setOnClickListener(new View.OnClickListener() {
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

    @NonNull
    @Override
    public allStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.storecard, parent, false);
        return new allStoreViewHolder(v);
    }

    public class allStoreViewHolder extends RecyclerView.ViewHolder{

        ImageView allStoresImage;
        TextView allStoresName;
        ConstraintLayout constLayout;
        CardView card;

        public allStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            allStoresImage = itemView.findViewById(R.id.storeImage);
            allStoresName = itemView.findViewById(R.id.storeName);
            constLayout = itemView.findViewById(R.id.storeCardClick);
            card = itemView.findViewById(R.id.cardView);
        }
    }

}

