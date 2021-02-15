package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    TextView tvChange,tvProceed,tvFinalprice,tv_COD;
    RecyclerView recyclerview;
    ArrayList<CategoryModel> catlist;
    CustomLoader loader;
    Preferences pref;
    String amt;
    CartListAdapter cartListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        //findViewById
        ivBack=findViewById(R.id.ivBack);
        tvChange=findViewById(R.id.tvChange);
        tvProceed=findViewById(R.id.tvProceed);
        recyclerview=findViewById(R.id.recyclerview);
        tvFinalprice=findViewById(R.id.tvFinalprice);
        tv_COD=findViewById(R.id.tv_COD);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        loader = new CustomLoader(CheckOutActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        catlist = new ArrayList<>();
        pref=new Preferences(this);

        if (Utils.isNetworkConnectedMainThred(this)) {
            getCart();

        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }

        //setonCLicklistener
        ivBack.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvProceed:
                if (Utils.isNetworkConnectedMainThred(this)) {
                   String userid="1";
                   String address="1";
                   String paymod="COD";

                    placeOrder(userid,address,paymod,amt);

                } else {
                    Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }



    public void getCart() {
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GETCART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();
                catlist.clear();
                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("Success").equalsIgnoreCase("true")) {
                        JSONArray array = jsonObject.getJSONArray("Product");
                        {
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject cat = array.getJSONObject(i);
                                CategoryModel catModel = new CategoryModel();
                                //adding the product to product list
                                catModel.setProduct_name(cat.getString("product_name"));
                                catModel.setSize(cat.getString("size"));
                                catModel.setProduct_id(cat.getString("product_id"));
                                catModel.setProduct_image(cat.getString("product_image"));
                                catModel.setMarked_price(cat.getString("marked_price"));
                                catModel.setSelling_price(cat.getString("selling_price"));
                                catModel.setCompany(cat.getString("company"));
                                catModel.setQty(cat.getString("qty"));
                                catlist.add(catModel);
                                double finalamt=0.0;

                                Double qty= Double.valueOf(cat.getString("qty"));
                                Double price= Double.valueOf(cat.getString("selling_price"));
                                finalamt = finalamt +  price* qty;
                                 amt=new Double(finalamt).toString();
                                tvFinalprice.setText(amt);
                                Log.e("EW",""+finalamt);
                            }
                        }
                    } else {
                       // layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(CheckOutActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    cartListAdapter = new CartListAdapter(CheckOutActivity.this, catlist);
                    recyclerview.setAdapter(cartListAdapter);

                } catch (JSONException e) {

                    Log.e("testerroor", e.toString());
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


    class CartListAdapter extends RecyclerView.Adapter<CheckOutActivity.CartListAdapter.Holder> {


        private Context mContext;
        private ArrayList<CategoryModel> categoryModel;
        CustomLoader loader;
        CheckOutActivity.CartListAdapter cartListAdapter;


        public CartListAdapter(Context context, ArrayList categoryModel) {
            this.categoryModel = categoryModel;
            this.mContext = context;

        }

        @Override
        public CheckOutActivity.CartListAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list, null);
            CheckOutActivity.CartListAdapter.Holder mh = new CheckOutActivity.CartListAdapter.Holder(v);

            return mh;
        }

        @Override
        public void onBindViewHolder(CheckOutActivity.CartListAdapter.Holder itemRowHolder, int i) {

            loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            CategoryModel catMod = categoryModel.get(i);

            itemRowHolder.tvOldPrice.setPaintFlags(itemRowHolder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            itemRowHolder.tvSubcategory.setText(catMod.getProduct_name());
            itemRowHolder.company.setText(catMod.getCompany());
            itemRowHolder.tvSize.setText(catMod.getSize());
            itemRowHolder.cart_item_number.setText(catMod.getQty());
            itemRowHolder.tvFinalprice.setText("\u20b9" + catMod.getSelling_price());
            itemRowHolder.tvOldPrice.setText("\u20b9" + catMod.getMarked_price());

            double discount = Double.parseDouble(catMod.getMarked_price()) - Double.parseDouble(catMod.getSelling_price());
            double dis_percent = (discount * 100) / Double.parseDouble(catMod.getMarked_price());
            DecimalFormat precision = new DecimalFormat("1");
            itemRowHolder.tvDiscount.setText(precision.format(dis_percent) + " % off");

            String image_url = AppConfig.IMAGE_PATH + categoryModel.get(i).getProduct_image();
            // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
            Glide.with(mContext)
                    .load(image_url)
                    .into(itemRowHolder.ivSubcategory);
            itemRowHolder.ivMinus.setVisibility(View.INVISIBLE);
            itemRowHolder.ivDelete.setVisibility(View.INVISIBLE);
            itemRowHolder.ivPlus.setVisibility(View.INVISIBLE);
            itemRowHolder.cart_item_number.setVisibility(View.INVISIBLE);
        }


        @Override
        public int getItemCount() {
            return categoryModel.size();
        }

        public class Holder extends RecyclerView.ViewHolder {

            protected TextView tvSubcategory;
            protected TextView tvDescription;
            protected TextView company;
            protected TextView tvSize;
            protected TextView tvFinalprice;
            protected TextView tvOldPrice;
            protected TextView tvDiscount;
            protected TextView cart_item_number;

            protected ImageView ivSubcategory;
            protected ImageView ivDelete;
            protected ImageView ivMinus;
            protected ImageView ivPlus;
            Layout layout_cart_empty;


            public Holder(View view) {
                super(view);

                this.tvSubcategory = view.findViewById(R.id.tvSubcategoryname);
                this.tvDescription = view.findViewById(R.id.tvDescription);
                this.ivSubcategory = view.findViewById(R.id.ivSubcategory);
                this.ivDelete = view.findViewById(R.id.ivDelete);
                this.ivMinus = view.findViewById(R.id.ivMinus);
                this.ivPlus = view.findViewById(R.id.ivPlus);
                this.company = view.findViewById(R.id.tvCompanyName);
                this.tvSize = view.findViewById(R.id.tvSize);
                this.tvFinalprice = view.findViewById(R.id.tvFinalprice);
                this.tvOldPrice = view.findViewById(R.id.tvOldPrice);
                this.tvDiscount = view.findViewById(R.id.tvDiscount);
                this.cart_item_number = view.findViewById(R.id.cart_item_number);
                // this.layout_cart_empty=view.findViewById(R.id.layout_cart_empty);
            }
        }


    }



    public void placeOrder(final String userid,final String address,final String paymod,final String amt) {
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.PLACEORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();
                catlist.clear();
                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("message").equalsIgnoreCase("true")) {
                       String mgs=jsonObject.getString("success_msg");
                        Toast.makeText(CheckOutActivity.this,
                                mgs,
                                Toast.LENGTH_LONG).show();

                    } else {
                        // layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(CheckOutActivity.this,
                                jsonObject.getString("success_msg"),
                                Toast.LENGTH_LONG).show();
                    }
                    cartListAdapter = new CartListAdapter(CheckOutActivity.this, catlist);
                    recyclerview.setAdapter(cartListAdapter);

                } catch (JSONException e) {

                    Log.e("testerroor", e.toString());
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
                params.put("user_id", userid);
                params.put("payment_mode", paymod);
                params.put("order_total", amt);
                params.put("address_id", address);

                Log.e("", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}