package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.SearchListAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivitySearchBinding;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding activitySearchBinding;
    List<String> arrayList= new ArrayList<>();
    SearchListAdapter adapter;
    String SubcatId;
    CustomLoader loader;
    Preferences pref;
    public static JSONArray jsonResponse;
    String Search;
    ArrayList<CategoryModel> arrayMap=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_search);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        pref=new Preferences(this);

        //custom loader
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        activitySearchBinding.search.setActivated(true);
        activitySearchBinding.search.setQueryHint("Search here...");
        activitySearchBinding.search.onActionViewExpanded();
        activitySearchBinding.search.setIconified(false);
        activitySearchBinding.search.clearFocus();
        activitySearchBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search=query;
                SubcatId="";
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.Filter(newText);
                return false;
            }
        });








        if (Utils.isNetworkConnectedMainThred(this)) {
            getProduct();
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }


    }

    //****************************************************************//
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
                                catModel.setProduct_id(cat.getString("id"));
                                arrayMap.add(catModel);
                                arrayList.add(cat.getString("product_name"));
                            }
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message")+response,
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    adapter = new SearchListAdapter(SearchActivity.this, arrayMap);
                    activitySearchBinding.rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    activitySearchBinding.rvSearch.setAdapter(adapter);

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
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}