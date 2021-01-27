package com.absolutecurepharma.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.absolutecurepharma.R;
import com.absolutecurepharma.adapter.CardPagerAdapter;
import com.absolutecurepharma.adapter.CategoryAdapter;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment  extends Fragment {
    View view;
    ViewPager bannerViewpager;
    RecyclerView rvCategory,rvProduct;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
    int currentPage = 0;
    Timer timer;
    long DELAY_MS = 1000;
    long PERIOD_MS = 3000;

    int [] array={R.drawable.banner1,R.drawable.banner2};
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &\nSupplements"};
    int [] productimage={R.drawable.product_image1,R.drawable.product_image2,R.drawable.product_image3,R.drawable.product_image4};
    String[] productname={"Paracetamol","Borncorid","Cetaphil","Head&shoulders"};
    String[] productsize={"500mg","200ml","118ml","400ml",};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        bannerViewpager=view.findViewById(R.id.bannerViewpager);
        rvCategory     =view.findViewById(R.id.rvCategory);
        rvProduct     =view.findViewById(R.id.rvProduct);

        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        categoryAdapter= new CategoryAdapter(getActivity(), categoryimage, categoryname) {
            @Override
            protected void onCategoryClick(View view, String str) {
                replaceFragmentWithAnimation(new SubcategoryFragment());
            }
        };
        productAdapter=new ProductAdapter(getActivity(),productimage,productname,productsize);
        rvProduct.setAdapter(productAdapter);
        rvCategory.setAdapter(categoryAdapter);


         TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabDots);
         tabLayout.setupWithViewPager(bannerViewpager, true);


        CardPagerAdapter mCardAdapterbelow = new CardPagerAdapter(getActivity(), array) {
            @Override
            protected void onBannerClick(View view, String str) {


            }
        };



        bannerViewpager.setAdapter(mCardAdapterbelow);
        mCardAdapterbelow.notifyDataSetChanged();
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



        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

}
