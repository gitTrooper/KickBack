package com.saidev.PaiseWala;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Bottom_user_profile extends Fragment {

    FirebaseUser user;
    TextView userNameInfo, userEmailInfo, confirmedCashbackInfo, PendingCashbackInfo;
    LinearLayout signOutbutton, earningsWithdrawBtn, appRateBtn, userTicket,to_use, helpBtn;
    loadingDialogBar dialogBar;


    public Bottom_user_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_user_profile, container, false);

        dialogBar = new loadingDialogBar(view.getContext());
        dialogBar.ShowDialog();

        signOutbutton = view.findViewById(R.id.logoutButton);
        appRateBtn = view.findViewById(R.id.userRating);
        userTicket = view.findViewById(R.id.ticketRaise);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String UserEmail = user.getEmail();

        earningsWithdrawBtn = view.findViewById(R.id.withdrawEarnings);
        userNameInfo = view.findViewById(R.id.userProfileName);
        userEmailInfo = view.findViewById(R.id.userProfileEmail);
        confirmedCashbackInfo = view.findViewById(R.id.userConfirmedCashback);
        PendingCashbackInfo = view.findViewById(R.id.userPendingCashback);
        to_use = view.findViewById(R.id.usage);
        helpBtn = view.findViewById(R.id.userHelp);

        userEmailInfo.setText(UserEmail);

        FirebaseFirestore.getInstance().collection(user.getEmail()).document("User Wallet Money").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot snapshot = task.getResult();
                            if (snapshot != null && snapshot.exists()){
                                confirmedCashbackInfo.setText("Rs."+(snapshot.getLong("walletValue").intValue()));
                                PendingCashbackInfo.setText("Rs."+(snapshot.getLong("PendingCashback").intValue())+"(Pending)");
                                dialogBar.HideDialog();
                            }
                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Unable To Fetch Cashback Details", Toast.LENGTH_SHORT).show();

                    }
                });

        FirebaseFirestore.getInstance().collection(user.getEmail()).document("OrderHistory")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot snapshot = task.getResult();
                                if (snapshot!=null&& snapshot.exists()){
                                    userNameInfo.setText(snapshot.getString("UserName"));
                                    dialogBar.HideDialog();
                                }
                            }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Unexpected Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        signOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

        earningsWithdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), payoutRequest.class));
            }
        });

        appRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.saidev.PaiseWala")));
            }
        });

        userTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String recipients= "johnhas1989@gmail.com";
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_TEXT,"[Send A ScreenShot Of the Ordered Product Via Amazon With Date Of Order. We Will Reply You Within 48 Hours :) ]");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        to_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), howToUse.class));
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String recipients= "johnhas1989@gmail.com";
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_TEXT,"[We Are Happy To Help You, Just Write Problem To Us And We Will Reply You Within 48 Hours :) ]");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        return view;
    }
}