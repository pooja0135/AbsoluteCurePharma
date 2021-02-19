package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.adapter.OrderListAdapter;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivityOrderListBinding;
import com.absolutecurepharma.databinding.ActivityRegisterBinding;
import com.absolutecurepharma.utils.Preferences;
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

public class OrderListActivity extends AppCompatActivity {
    ActivityOrderListBinding binding;
    CustomLoader loader;
    OrderListAdapter orderListAdapter;
    Preferences pref;
    ArrayList<CategoryModel> orderList;

    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);



        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderListActivity.this,MainActivity.class));
            }
        });

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref=new Preferences(this);
        orderList=new ArrayList<>();

        if (Utils.isNetworkConnectedMainThred(this)) {
          getOrders();
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        binding.rvOrder.setLayoutManager(new GridLayoutManager(this, 1));
       // binding.rvOrder.setAdapter(new OrderListAdapter(this,categoryimage,categoryname));

    }


    public void getOrders(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.MYORDERS,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Orderssss", response);
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
//                                //adding the product to product list
                                catModel.setProduct_name(cat.getString("product_name"));
                               catModel.setCompany(cat.getString("company"));
                                catModel.setProduct_image(cat.getString("product_image"));
                                catModel.setSelling_price(cat.getString("selling_price"));
                                catModel.setOrder_id(cat.getString("order_id"));
                                catModel.setOrder_total(cat.getString("order_total"));
                                catModel.setOrder_date(cat.getString("order_date"));
                                catModel.setOrder_status(cat.getString("order_status"));
                                catModel.setDelivery_date(cat.getString("delivery_date"));
                                catModel.setProduct_qty(cat.getString("product_qty"));
                                catModel.setTotal_price(cat.getString("total_price"));


                                orderList.add(catModel);
                            }
                        }
                    }
                    else {
                        Toast.makeText(OrderListActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    orderListAdapter = new OrderListAdapter(OrderListActivity.this,orderList );
                    binding.rvOrder.setAdapter(orderListAdapter);

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
                params.put("user_id", "14");

                Log.e("",""+params);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(OrderListActivity.this).add(stringRequest);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}