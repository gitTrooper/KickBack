package com.saidev.PaiseWala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText log_activity_user_email, log_activity_user_password;
    Button activity_login_btn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    loadingDialogBar dialogBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        log_activity_user_email = findViewById(R.id.log_user_email);
        log_activity_user_password = findViewById(R.id.log_user_password);
        dialogBar = new loadingDialogBar(this);

        activity_login_btn = findViewById(R.id.login_btn);
        activity_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_user();
            }
        });

    }

    public void onLoginClick(View view){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    private void login_user(){
        String user_email = log_activity_user_email.getText().toString();
        String user_password = log_activity_user_password.getText().toString();

        if (TextUtils.isEmpty(user_email)){
            log_activity_user_email.setError("User Name Cannot Be Empty");
            log_activity_user_email.requestFocus();
        }else if (TextUtils.isEmpty(user_password)){
            log_activity_user_password.setError("Please Enter Password");
            log_activity_user_password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        dialogBar.HideDialog();
                    }
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
    }

}