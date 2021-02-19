package com.absolutecurepharma.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.R;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.SubCategoryAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;
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

public class SubcategoryFragment extends Fragment {

    View view;
    RecyclerView rvSubCategory;
    SubCategoryAdapter subCategoryAdapter;
    CustomLoader loader;
    ArrayList<CategoryModel> subcatlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.subcategory_fragment, container, false);
        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        rvSubCategory=view.findViewById(R.id.rvSubCategory);
        loader = new CustomLoader(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        subcatlist=new ArrayList<>();

        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        getProductById(Constants.cat_id);
        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction =getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }
    public void getProductById(final String cat_id){
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
                                catModel.setDescription(cat.getString("description"));
                                subcatlist.add(catModel);

                                //replaceFragmentWithAnimation(new SubcategoryFragment());

                            }
                            subCategoryAdapter = new SubCategoryAdapter(getContext(), subcatlist) {
                                @Override
                                protected void onSubCategoryClick(View view, String str) {

                                }
                            };
                            rvSubCategory.setAdapter(subCategoryAdapter);
                        }
                    }
                    else {
                        Toast.makeText(getContext(),
                                jsonObject.getString("message")+response,
                                Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
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
                params.put("cat_id", cat_id);

                Log.e("",""+params);
                return params;
            }
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
