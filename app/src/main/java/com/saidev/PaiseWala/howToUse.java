package com.saidev.PaiseWala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.saidev.PaiseWala.adapter.viewPagerAdapter;
import com.saidev.PaiseWala.viewmodel.viewPagerModel;

import java.util.ArrayList;

public class howToUse extends AppCompatActivity {

    ViewPager2 viewPager2;
    ArrayList<viewPagerModel> viewPagerModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        viewPager2 = findViewById(R.id.stepsviewPager);
        int[] stepimg = {R.drawable.usecardone, R.drawable.usecardtwo, R.drawable.usecardthree};
        String[] heading = {"Step 1","Step 2","Step 3"};
        String[] description = {"Just Browse Through Your Favourite Category or Tap The Banners",
                "Shop Your Favourite Products From Amazon",
                "Woahhh!! You Will Receive Your Cashback Once Your Order Is Confirmed and Return Period Is Expired"};

        viewPagerModelArrayList = new ArrayList<>();

        for(int i = 0; i<stepimg.length; i++){
            viewPagerModel model = new viewPagerModel(stepimg[i], heading[i], description[i]);
            viewPagerModelArrayList.add(model);
        }

        viewPagerAdapter adapter = new viewPagerAdapter(viewPagerModelArrayList);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(true);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);



    }
}