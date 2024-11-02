package com.saidev.PaiseWala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.saidev.PaiseWala.adapter.payoutHistoryAdapter;
import com.saidev.PaiseWala.model.payoutDataModel;
import com.saidev.PaiseWala.model.payoutHistoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class payoutRequest extends AppCompatActivity {

    TextView cnfAmount, pending, total;
    Button amountWithdrawal;
    Integer userConfirmedAmount;
    Integer userPendingAmount;
    DatabaseReference payoutData;
    RecyclerView withdrawalRequest;
    FirebaseFirestore firestore;
    payoutHistoryAdapter adapter;
    ArrayList<payoutHistoryModel> modelList;
    int i=0;
    Integer requestAmount;
    loadingDialogBar dialogBar;
    String name = "Rishi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payout_request);

        cnfAmount = findViewById(R.id.confirmedAmount);
        pending = findViewById(R.id.pendingAmount);
        total = findViewById(R.id.totalEarnings);
        amountWithdrawal = findViewById(R.id.rewardWithdrawalBtn);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String purchaseDate = df.format(currentTime);
        dialogBar = new loadingDialogBar(this);
        dialogBar.ShowDialog();

        withdrawalRequest = findViewById(R.id.payoutHistoryRecycler);
        withdrawalRequest.setHasFixedSize(true);
        withdrawalRequest.setLayoutManager(new LinearLayoutManager(this));
        firestore = FirebaseFirestore.getInstance();


        modelList = new ArrayList<payoutHistoryModel>();
        adapter = new payoutHistoryAdapter(this, modelList);
        withdrawalRequest.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        payoutData = FirebaseDatabase.getInstance().getReference("PAYOUT_REQUESTS");

        FirebaseFirestore.getInstance().collection(user.getEmail()).document("User Wallet Money").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot != null && snapshot.exists()){
                                pending.setText("Rs."+(snapshot.getLong("PendingCashback").intValue()));
                                cnfAmount.setText("Rs."+(snapshot.getLong("ConfirmedCashback").intValue()));
                                total.setText("Rs."+ snapshot.getLong("walletValue").intValue());
                                userConfirmedAmount = snapshot.getLong("ConfirmedCashback").intValue();
                                userPendingAmount = snapshot.getLong("PendingCashback").intValue();
                                requestAmount = snapshot.getLong("ConfirmedCashback").intValue();
                                dialogBar.HideDialog();
                            }
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(payoutRequest.this, "Unable To Fetch Cashback Details", Toast.LENGTH_SHORT).show();
                        dialogBar.HideDialog();
                    }
                });




        amountWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userConfirmedAmount < 200){
                    Toast.makeText(payoutRequest.this, "You have less than Rs.200 Confirmed Cashback", Toast.LENGTH_SHORT).show();
                }
                else {
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String REQUEST_DATE = df.format(currentTime);
                    SimpleDateFormat REQUEST_TIME = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
                    String reqTime = REQUEST_TIME.format(currentTime);
                    Map<String, Integer> userRequestedAmount = new HashMap<>();
                    userRequestedAmount.put("WithdrawalAmount", requestAmount);
                    userRequestedAmount.put("WithdrawStatus", 0);
                    FirebaseFirestore.getInstance().collection(user.getEmail()).document("PAYOUT").collection("REQUEST").document(REQUEST_DATE+reqTime).set(userRequestedAmount);


                    String userPhoneNumber = "";
                    String userEmail = user.getEmail();
                    String userAmount = String.valueOf(userConfirmedAmount);
                    payoutDataModel model = new payoutDataModel(userPhoneNumber,userEmail, userAmount);
                    payoutData.push().setValue(model);
                    Toast.makeText(payoutRequest.this, "Amount Requested Successfully", Toast.LENGTH_SHORT).show();


                    //UPDATE USER WALLET DATA AFTER REQUESTING AMOUNT

                    Map<String, Integer> userWallerData = new HashMap<>();
                    Integer cnfCashback = 0;
                    Integer walletValue = cnfCashback+userPendingAmount;
                    userWallerData.put("walletValue", walletValue);
                    userWallerData.put("PendingCashback", userPendingAmount);
                    userWallerData.put("ConfirmedCashback", cnfCashback);
                    Map<String, String> userWalletData1 = new HashMap<>();
                    userWalletData1.put("showHistory", "YES");
                    userWalletData1.put("UserName", name);
                    FirebaseFirestore.getInstance().collection(user.getEmail()).document("User Wallet Money").set(userWallerData);
                    FirebaseFirestore.getInstance().collection(user.getEmail()).document("User Wallet Money").collection("userData").document().set(userWalletData1);


                }
            }
        });

        FirebaseFirestore.getInstance().collection(user.getEmail()).document("PAYOUT").collection("REQUEST").document().get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                                firestore.collection(user.getEmail()).document("PAYOUT").collection("REQUEST").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        for (DocumentChange dc : value.getDocumentChanges()) {
                                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                                modelList.add(dc.getDocument().toObject(payoutHistoryModel.class));
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                        }
                    }
                });

    }
}