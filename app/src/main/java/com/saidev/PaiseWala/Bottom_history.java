package com.saidev.PaiseWala;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saidev.PaiseWala.adapter.purchaseHistoryAdapter;
import com.saidev.PaiseWala.model.purchaseHistoyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class Bottom_history extends Fragment {

    RecyclerView historyRecycler;
    ArrayList<purchaseHistoyModel> modelArrayList;
    purchaseHistoryAdapter myAdatper;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    loadingDialogBar dialogBar;

    public Bottom_history() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bottom_history, container, false);
        dialogBar = new loadingDialogBar(view.getContext());
        dialogBar.ShowDialog();
        historyRecycler = view.findViewById(R.id.purchaseHistoryRecycler);
        historyRecycler.setHasFixedSize(true);
        historyRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserEmail = currentUser.getEmail();
        db = FirebaseFirestore.getInstance();
        modelArrayList = new ArrayList<purchaseHistoyModel>();
        myAdatper = new purchaseHistoryAdapter(getContext(), modelArrayList);
        historyRecycler.setAdapter(myAdatper);
        FirebaseFirestore.getInstance().collection(currentUserEmail).document("OrderHistory").get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DocumentSnapshot snapshot = task.getResult();
                                        if (!Objects.equals(snapshot.getString("showHistory"), "NO")){
                                            db.collection(currentUserEmail).document("OrderHistory").collection("History").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    for (DocumentChange dc : value.getDocumentChanges()){
                                                        if (dc.getType() == DocumentChange.Type.ADDED){
                                                            modelArrayList.add(dc.getDocument().toObject(purchaseHistoyModel.class));
                                                        }
                                                        myAdatper.notifyDataSetChanged();
                                                        dialogBar.HideDialog();
                                                    }
                                                }
                                            });
                                        }
                                        else {
                                            historyRecycler.setBackgroundResource(R.drawable.nohist);
                                            dialogBar.HideDialog();
                                        }
                                    }
                            }
                        });

        return view;
    }
}