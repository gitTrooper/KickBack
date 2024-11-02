package com.saidev.PaiseWala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class storeDetailsPage extends AppCompatActivity {

    ImageView storeImageView;
    TextView storeCasbackRate, storeTermsConditions;
    loadingDialogBar dialogBar;
    String nameIntent;
    Intent in_tent;
    Button SEND_USER_TO_TARGET_STORE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_details_page);

        //LOADING BAR
        dialogBar = new loadingDialogBar(this);
        dialogBar.ShowDialog();


        //GETTING NAME FROM INTENT
        in_tent = getIntent();
        nameIntent = in_tent.getStringExtra("storeName");

        //-------------------------
        CollapsingToolbarLayout bar = findViewById(R.id.collapsingToolbar);
        bar.setCollapsedTitleTextColor(Color.WHITE);
        bar.setTitle(nameIntent);

        //ID INITIALIZATION
        storeImageView = findViewById(R.id.storeDetailImage);
        storeTermsConditions = findViewById(R.id.TermsConditionsText);
        storeCasbackRate = findViewById(R.id.cashbackRatesText);
        SEND_USER_TO_TARGET_STORE = findViewById(R.id.goToButton);


        //FIRESTORE DATA FETCHING
        FirebaseFirestore.getInstance().collection("STORES").document(nameIntent).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot!=null&& snapshot.exists()){
                        storeCasbackRate.setText(snapshot.getString("cashbackRates"));
                        storeTermsConditions.setText(snapshot.getString("termsConditions").replaceAll("/n", "\n"));
                        Glide.with(storeDetailsPage.this).load(snapshot.getString("imageLink")).into(storeImageView);
                        dialogBar.HideDialog();
                    }
                }
            }
        });

        SEND_USER_TO_TARGET_STORE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(storeDetailsPage.this, onclick_productView.class);
                i.putExtra("url", in_tent.getStringExtra("storeAffiliateLink"));
                startActivity(i);
            }
        });

    }
}