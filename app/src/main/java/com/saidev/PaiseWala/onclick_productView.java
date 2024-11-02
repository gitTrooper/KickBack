package com.saidev.PaiseWala;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class onclick_productView extends AppCompatActivity {

    WebView webView;
    String url;
    FirebaseUser currentUser;
    Dialog purchaseDialog;
    String purchaseTime;
    String productUrl;
    loadingDialogBar dialogBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_product_view);

        Intent url_intent = getIntent();
        String productLink = url_intent.getStringExtra("url");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        webView = findViewById(R.id.productWebView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String currentUserEmail = currentUser.getEmail();
        purchaseDialog = new Dialog(this);
        dialogBar = new loadingDialogBar(this);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {

                if (url.contains("/dp/")){
                    productUrl = url;
                }
                else if (url.contains("thankyou")){
                    dialogBar.ShowDialog();
                    String link =  productUrl;
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String purchaseDate = df.format(currentTime);
                    SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
                    purchaseTime = dt.format(currentTime);
                    showPurchaseDialog();
                    String Status = "Did You Just Shop On Amazon?";
                    Map<String, String> inputData = new HashMap<>();
                    inputData.put("ProductName",link);
                    inputData.put("PurchaseDate",purchaseDate);
                    inputData.put("status", Status);

                    FirebaseFirestore.getInstance().collection(currentUserEmail).document("OrderHistory").collection("History")
                            .document(purchaseDate+" "+purchaseTime).set(inputData);
                    FirebaseFirestore.getInstance().collection(currentUserEmail).document("OrderHistory").update("showHistory", "YES");
                    dialogBar.HideDialog();

                }

                super.onPageFinished(view, url);
            }
        });


        webView.loadUrl(productLink);
    }

    private void showPurchaseDialog(){
        purchaseDialog.setContentView(R.layout.purchasedialog);
        purchaseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        purchaseDialog.show();
    }

}