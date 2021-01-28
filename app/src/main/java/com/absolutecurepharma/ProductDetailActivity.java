package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.absolutecurepharma.adapter.ProductDetailPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class ProductDetailActivity extends AppCompatActivity {

    ViewPager bannerViewpager;
    ProductDetailPagerAdapter productDetailPagerAdapter;
    int [] array={R.drawable.product_image1,R.drawable.product_image1};
    int currentPage = 0;
    Timer timer;
    long DELAY_MS = 1000;
    long PERIOD_MS = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        bannerViewpager=findViewById(R.id.bannerViewpager);
        productDetailPagerAdapter=new ProductDetailPagerAdapter(this,array) {
            @Override
            protected void onBannerClick(View view, String str) {

            }
        };

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(bannerViewpager, true);

        bannerViewpager.setAdapter(productDetailPagerAdapter);
        productDetailPagerAdapter.notifyDataSetChanged();
        bannerViewpager.setOffscreenPageLimit(2);
        bannerViewpager.setClipToPadding(false);
        bannerViewpager.setCurrentItem(0, true);
        bannerViewpager.setPageMargin(10);

        final int NUM_PAGES = array.length;
        final Handler handler = new Handler();

        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                bannerViewpager.setCurrentItem(currentPage++, true);


            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


    }
}