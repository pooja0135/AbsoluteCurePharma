package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.adapter.CartListAdapter;
import com.absolutecurepharma.adapter.CategoryAdapter;
import com.absolutecurepharma.adapter.ProductListAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.fragment.ProductListFragment;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartListActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvCart;
    CartListAdapter cartListAdapter;
    LinearLayout llcartItem;

    //Textview
    TextView tvHeaderText;
    TextView tvProceed;
    TextView tvFinalprice;

    Button bAddNew;
    ImageView ivBack;
    CustomLoader loader;
    LinearLayout layout_cart_empty;
    ArrayList<CategoryModel>catlist;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &Supplements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        llcartItem = findViewById(R.id.llcartItem);
        bAddNew = findViewById(R.id.bAddNew);
        ivBack = findViewById(R.id.ivBack);
        rvCart = findViewById(R.id.rvCart);
        tvFinalprice = findViewById(R.id.tvFinalprice);
        tvProceed = findViewById(R.id.tvProceed);
        loader = new CustomLoader(CartListActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        catlist=new ArrayList<>();

        if (Utils.isNetworkConnectedMainThred(this)) {
            getCart();

        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartListAdapter= new CartListAdapter(this,catlist) {

            @Override
            protected void onPlusClick(View view, String str) {

            }

            @Override
            protected void onMinusClick(View view, String str) {

            }

            @Override
            protected void onDeleteClick(View view, String str) {

            }
        };

        rvCart.setAdapter(cartListAdapter);

        //setonCLicklistener
        ivBack.setOnClickListener(this);
        bAddNew.setOnClickListener(this);
        tvProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;

            case R.id.bAddNew:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tvProceed:
                startActivity(new Intent(this,CheckOutActivity.class));
                break;
        }
    }


    public void getCart(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GETCART,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
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
//                                catModel.setCat_id(cat.getString("cat_id"));
//                                catModel.setCat_name(cat.getString("cat_name"));
//                                catModel.setCat_image(cat.getString("cat_image"));
                                catlist.add(catModel);

                            }
                        }
                    }
                    else {
                        Toast.makeText(CartListActivity.this,
                                jsonObject.getString("message")+response,
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    cartListAdapter = new CartListAdapter(CartListActivity.this, catlist) {
                        @Override
                        protected void onPlusClick(View view, String str) {

                        }

                        @Override
                        protected void onMinusClick(View view, String str) {

                        }

                        @Override
                        protected void onDeleteClick(View view, String str) {

                        }
                    };
                    rvCart.setAdapter(cartListAdapter);
                } catch (JSONException e) {

                    Log.e("testerroor",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", "1");

                Log.e("", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}