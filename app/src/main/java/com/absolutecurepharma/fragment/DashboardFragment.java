package com.absolutecurepharma.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.R;

import com.absolutecurepharma.adapter.CardPagerAdapter;
import com.absolutecurepharma.adapter.CategoryAdapter;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    long DELAY_MS = 5000;
    long PERIOD_MS = 5000;
    ArrayList<CategoryModel>catlist;
    ArrayList<CategoryModel>prodlist;
    TextView tvCount;

    CustomLoader loader;

    int [] array={R.drawable.banner1,R.drawable.banner2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dashboard_fragment, container, false);


        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        bannerViewpager=view.findViewById(R.id.bannerViewpager);
        rvCategory     =view.findViewById(R.id.rvCategory);
        rvProduct     =view.findViewById(R.id.rvProduct);



        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        catlist=new ArrayList<>();
        prodlist=new ArrayList<>();
        if (Utils.isNetworkConnectedMainThred(getActivity())) {
            getCategory();
            getProduct();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }



        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
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


    //*******************************************************************//
    public void getCategory(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.GETCATEGORY,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("Success").equalsIgnoreCase("true")) {
                        JSONArray array = jsonObject.getJSONArray("Category");
                        {
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject cat = array.getJSONObject(i);
                                CategoryModel catModel = new CategoryModel();
                                //adding the product to product list
                                catModel.setCat_id(cat.getString("cat_id"));
                                catModel.setCat_name(cat.getString("cat_name"));
                                catModel.setCat_image(cat.getString("cat_image"));
                                catlist.add(catModel);

                            }
                        }
                    }
                    else {
                        Toast.makeText(getContext(),
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    categoryAdapter = new CategoryAdapter(getContext(), catlist);
                    rvCategory.setAdapter(categoryAdapter);
                } catch (JSONException e) {

                    Log.e("testerroor",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
    public void getProduct(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GETPRODUCT,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Product", response);
                loader.dismiss();
                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("Success").equalsIgnoreCase("true")) {
                        JSONArray array = jsonObject.getJSONArray("Product");
                        {
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject cat = array.getJSONObject(i);
                                CategoryModel catModel = new CategoryModel();
                                //adding the product to product list
                                catModel.setProduct_name(cat.getString("product_name"));
                                catModel.setProduct_image(cat.getString("product_image"));
                                catModel.setMarked_price(cat.getString("marked_price"));
                                catModel.setSelling_price(cat.getString("selling_price"));
                                catModel.setSize(cat.getString("size"));
                                catModel.setProduct_id(cat.getString("id"));
                                prodlist.add(catModel);
                            }
                        }
                    }
                    else {
                        Toast.makeText(getContext(),
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    productAdapter = new ProductAdapter(getContext(), prodlist);
                    rvProduct.setAdapter(productAdapter);

                } catch (JSONException e) {

                    Log.e("testerroor",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("cat_id", "");

                Log.e("",""+params);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}
