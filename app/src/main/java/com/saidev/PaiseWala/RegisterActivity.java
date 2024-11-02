package com.saidev.PaiseWala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saidev.PaiseWala.model.userDataHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText reg_activity_user_name , reg_activity_user_password , reg_activity_user_email , reg_activity_user_phone;
    Button activity_reg_user_btn;
    FirebaseAuth mAuth;
    loadingDialogBar dialogBar;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        changeStatusBarColor();

        mAuth = FirebaseAuth.getInstance();
        activity_reg_user_btn = findViewById(R.id.reg_user_btn);
        reg_activity_user_name = findViewById(R.id.reg_user_name);
        reg_activity_user_password = findViewById(R.id.reg_user_password);
        reg_activity_user_email = findViewById(R.id.reg_user_email);
        reg_activity_user_phone = findViewById(R.id.reg_user_phone);
        dialogBar = new loadingDialogBar(this);


        activity_reg_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBar.ShowDialog();
                createNewUser();
                saveUserData();
            }
        });


    }

    public void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));

        }
    }

    //THIS IS THE FUNCTION TO REGISTER A NEW USER-----------

    public void onLoginClick(View view){
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

    private void createNewUser(){

        String user_name = reg_activity_user_name.getText().toString();
        String user_password = reg_activity_user_password.getText().toString();
        String user_email = reg_activity_user_email.getEditableText().toString();
        String user_phone = reg_activity_user_phone.getText().toString();

        if (TextUtils.isEmpty(user_name)){
            reg_activity_user_name.setError("User Name Cannot Be Empty");
            reg_activity_user_name.requestFocus();
        }else if (TextUtils.isEmpty(user_password)){
            reg_activity_user_password.setError("Please Enter Password");
            reg_activity_user_password.requestFocus();
        }else if (TextUtils.isEmpty(user_email)){
            reg_activity_user_email.setError("Please Enter A Valid Email");
            reg_activity_user_email.requestFocus();
        }else if (TextUtils.isEmpty(user_phone)){
            reg_activity_user_phone.setError("Please Enter Phone Number");
            reg_activity_user_phone.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(RegisterActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


    //THIS IS THE FUNCTION TO SAVE USER DATA IN FIREBASE REALTIME DATABASE-----

    private void saveUserData(){
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("user_Info");

        name = reg_activity_user_name.getText().toString();
        String password = reg_activity_user_password.getText().toString();
        String email = reg_activity_user_email.getEditableText().toString().trim();
        String phone = reg_activity_user_phone.getText().toString();

        Map<String, Integer> userWallerData = new HashMap<>();
        Integer cnfCashback = 25;
        Integer userPendingAmount = 0;
        Integer walletValue = cnfCashback+userPendingAmount;
        userWallerData.put("walletValue", walletValue);
        userWallerData.put("PendingCashback", userPendingAmount);
        userWallerData.put("ConfirmedCashback", cnfCashback);
        FirebaseFirestore.getInstance().collection(email).document("User Wallet Money").set(userWallerData);


        Map<String, Integer> userCoins = new HashMap<>();
        Integer totalCoins = 50;
        userCoins.put("userTotalCoins", totalCoins);
        FirebaseFirestore.getInstance().collection(email).document("Reward Coins").set(userCoins);


        Map<String, String> userWalletData1 = new HashMap<>();
        userWalletData1.put("UserName", name);
        userWalletData1.put("showHistory", "NO");
        FirebaseFirestore.getInstance().collection(email).document("OrderHistory").set(userWalletData1);


        userDataHelper dataHelper = new userDataHelper(name,password,email,phone);
        reference.child(phone).setValue(dataHelper);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                dialogBar.HideDialog();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        }, 4000);


    }





}