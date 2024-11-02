package com.saidev.PaiseWala;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.saidev.PaiseWala.adapter.allStoreAdapter;
import com.saidev.PaiseWala.adapter.budgetAdapter;
import com.saidev.PaiseWala.adapter.storeAdapter;
import com.saidev.PaiseWala.model.budget_model;
import com.saidev.PaiseWala.model.storeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Bottom_home extends Fragment {

    View view;
    private ImageSlider home_top_slider;
    RecyclerView storeCardRecycler;
    ArrayList<storeModel> modelArrayList;
    storeAdapter adapter;
    RecyclerView allStores;
    loadingDialogBar dialogBar;
    String link;
    allStoreAdapter fullStoreAdapter;
    FirebaseUser user;
    TextView ConfirmedCashback, PendingCashback;
    RecyclerView multiView;
    ArrayList<budget_model> budgetModelArrayList;
    budgetAdapter adapterBudget;

    public Bottom_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bottom_home_frag, container, false);
        dialogBar = new loadingDialogBar(view.getContext());
        home_top_slider = view.findViewById(R.id.image_slider);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ConfirmedCashback = view.findViewById(R.id.confirmedCash);
        PendingCashback = view.findViewById(R.id.pendingCash);

        //TOOLBAR
        CollapsingToolbarLayout titleToolbar = view.findViewById(R.id.collapsingToolbar);
        titleToolbar.setCollapsedTitleTextColor(Color.WHITE);
        titleToolbar.setTitle("PaiseWala");


        //MULTI VIEW RECYCLER INITIALIZATION
        multiView = view.findViewById(R.id.multiViewRecycler);
        multiView.setHasFixedSize(true);
        multiView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        budgetModelArrayList = new ArrayList<budget_model>();
        adapterBudget = new budgetAdapter(view.getContext(), budgetModelArrayList);
        multiView.setAdapter(adapterBudget);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MultiViewLinks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    budget_model model = dataSnapshot.getValue(budget_model.class);
                    budgetModelArrayList.add(model);
                }
                adapterBudget.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TRENDING STORE RECYCLER INITIALIZATION----------------->

        storeCardRecycler = view.findViewById(R.id.trendStore);
        storeCardRecycler.setHasFixedSize(true);
        storeCardRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 4));
        modelArrayList = new ArrayList<storeModel>();
        adapter = new storeAdapter(view.getContext(),modelArrayList);
        storeCardRecycler.setAdapter(adapter);
        dialogBar.ShowDialog();
        //ALL STORES RECYCLER INITIALIZATION---------------------->

        allStores = view.findViewById(R.id.allStoresView);
        allStores.setLayoutManager(new GridLayoutManager(view.getContext(), 4));

        FirebaseRecyclerOptions<storeModel> options = new FirebaseRecyclerOptions.Builder<storeModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("AllStores"),storeModel.class).build();

        fullStoreAdapter = new allStoreAdapter(options);
        fullStoreAdapter.startListening();
        allStores.setAdapter(fullStoreAdapter);



        //TRENDING STORE FIREBASE DATA READING

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TrendingStores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    storeModel model = dataSnapshot.getValue(storeModel.class);
                    modelArrayList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //UPDATING USER EARNINGS AND FETCHING DATA FROM FIREBASE
        FirebaseFirestore.getInstance().collection(user.getEmail()).document("User Wallet Money").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot!=null && snapshot.exists()){
                                ConfirmedCashback.setText(String.valueOf(snapshot.getLong("walletValue").intValue()));
                                PendingCashback.setText(String.valueOf(snapshot.getLong("PendingCashback").intValue()));
                            }
                        }
                    }
                });


        //BANNER SLIDER INITIALIZATION------------------------->

       final List<SlideModel> banner_images = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("HOME_BANNER_SLIDER")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren()) {

                            banner_images.add(new SlideModel(data.child("banner_img_link").getValue().toString(), data.child("banner_title").getValue().toString(),ScaleTypes.FIT));
                        }
                        home_top_slider.setImageList(banner_images, ScaleTypes.FIT);
                        dialogBar.HideDialog();
                        home_top_slider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {

                                FirebaseDatabase.getInstance().getReference("slideLinks").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        link = snapshot.child("links"+i).getValue().toString();
                                        Intent in = new Intent(view.getContext(), onclick_productView.class);
                                        in.putExtra("url", link);
                                        startActivity(in);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        return view;
    }




}