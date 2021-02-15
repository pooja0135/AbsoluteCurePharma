package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.ProductDetailPagerAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivityProductDetailBinding;
import com.absolutecurepharma.utils.Preferences;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener{

    ViewPager bannerViewpager;
    ProductDetailPagerAdapter productDetailPagerAdapter;
    int [] array={R.drawable.product_image1,R.drawable.product_image1};
    int currentPage = 0;
    Timer timer;
    long DELAY_MS = 1000;
    long PERIOD_MS = 3000;
    ImageView ivBack,ivCart;
    CustomLoader loader;
    String productName,companyName,markerdPrice,sellingPrice,sizes,details,prodID;
    TextView tvProductName;
    ActivityProductDetailBinding binding;
    Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref=new Preferences(this);
        bannerViewpager=findViewById(R.id.bannerViewpager);
        productDetailPagerAdapter=new ProductDetailPagerAdapter(this,array) {
            @Override
            protected void onBannerClick(View view, String str) {

            }
        };

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(bannerViewpager, true);

        binding. bannerViewpager.setAdapter(productDetailPagerAdapter);
        productDetailPagerAdapter.notifyDataSetChanged();
        binding.bannerViewpager.setOffscreenPageLimit(2);
        binding.bannerViewpager.setClipToPadding(false);
        binding.bannerViewpager.setCurrentItem(0, true);
        binding.bannerViewpager.setPageMargin(10);

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

        if (Utils.isNetworkConnectedMainThred(this)) {

            getProductDetails(Constants.product_id);
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        //setOnClickListener
        binding.ivBack.setOnClickListener(this);
        binding.ivCart.setOnClickListener(this);
        binding.tvAddToCart.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivCart:
                startActivity(new Intent(this,CartListActivity.class));
                break;
            case R.id.tvAddToCart:

                prodID=Constants.product_id;
               addToCart(prodID,sellingPrice,sellingPrice);
                break;
        }
    }
    public void getProductDetails(final String product_id){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.PRODUCTDETAILS,new Response.Listener<String>() {
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
                                JSONObject prod = array.getJSONObject(i);
                                productName=prod.getString("product_name");
                                companyName=prod.getString("company");
                                sizes=prod.getString("size");
                                details=prod.getString("description");
                                markerdPrice=prod.getString("marked_price");
                                sellingPrice=prod.getString("selling_price");

                                double discount= Double.parseDouble(markerdPrice)-Double.parseDouble(sellingPrice);
                                double dis_percent=(discount*100)/Double.parseDouble(markerdPrice);
                                DecimalFormat precision = new DecimalFormat("1");

                              binding.tvProductName.setText(""+productName);
                              binding.tvCompanyName.setText(""+companyName);
                              binding.tvDescription.setText(""+details);
                              binding.tvFinalprice.setText("\u20b9"+""+markerdPrice);
                              binding.tvOldPrice.setText("\u20b9"+""+sellingPrice);
                              binding.tvDiscount.setText(""+precision.format(dis_percent)+" % off");
                            }
                        }
                    }
                    else {
                        Toast.makeText(ProductDetailActivity.this,
                                jsonObject.getString("message")+response,
                                Toast.LENGTH_LONG).show();
                    }

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
                params.put("product_id",product_id) ;
                Log.e("",""+params);
                return params;
            }
        };
        Volley.newRequestQueue(ProductDetailActivity.this).add(stringRequest);
    }

    public void addToCart(final String prodId, final String sellingPrice, final String totalPrice){
        loader.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.ADDTOCART,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Product", response);
                loader.dismiss();
                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("message").equalsIgnoreCase("true")) {
                        Toast.makeText(ProductDetailActivity.this,
                                jsonObject.getString("success_msg"),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ProductDetailActivity.this,
                                jsonObject.getString("success_msg"),
                                Toast.LENGTH_LONG).show();
                    }

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
                params.put("prod_id",prodId) ;
                params.put("user_id",pref.get(Constants.USERID)) ;
                params.put("qty","1") ;
                params.put("price",sellingPrice) ;
                params.put("total",totalPrice) ;

                Log.e("ADD TO CART",""+params);
                return params;
            }
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(ProductDetailActivity.this).add(stringRequest);
    }

}