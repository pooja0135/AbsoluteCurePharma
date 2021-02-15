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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    LinearLayout layout_cart_empty,linear;
    ArrayList<CategoryModel> catlist;

    Preferences pref;


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
        linear = findViewById(R.id.linear);
        layout_cart_empty = findViewById(R.id.layout_cart_empty);
        loader = new CustomLoader(CartListActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        catlist = new ArrayList<>();
        pref=new Preferences(this);

        if (Utils.isNetworkConnectedMainThred(this)) {
            getCart();

        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartListAdapter);

        //setonCLicklistener
        ivBack.setOnClickListener(this);
        bAddNew.setOnClickListener(this);
        tvProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;

            case R.id.bAddNew:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tvProceed:
                startActivity(new Intent(this, CheckOutActivity.class));
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

                double finalamt=0.0;
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


                                Double qty= Double.valueOf(cat.getString("qty"));
                                Double price= Double.valueOf(cat.getString("selling_price"));
                                finalamt = finalamt +  price* qty;
                                String amt=new Double(finalamt).toString();
                            tvFinalprice.setText(amt);
                                Log.e("EW",""+finalamt);
                            }
                        }
                    } else {
                        Log.e("gfdd","hgt");
                        llcartItem.setVisibility(View.GONE);
                        layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(CartListActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    cartListAdapter = new CartListAdapter(CartListActivity.this, catlist);
                    rvCart.setAdapter(cartListAdapter);

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
                params.put("user_id", pref.get(Constants.USERID));

                Log.e("", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.Holder> {


        private Context mContext;
        private ArrayList<CategoryModel> categoryModel;
        CustomLoader loader;
        CartListAdapter cartListAdapter;


        public CartListAdapter(Context context, ArrayList categoryModel) {
            this.categoryModel = categoryModel;
            this.mContext = context;

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list, null);
            Holder mh = new Holder(v);

            return mh;
        }

        @Override
        public void onBindViewHolder(Holder itemRowHolder, int i) {

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

            // Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivSubcategory);

            itemRowHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userId = pref.get(Constants.USERID);
                    String prodid = catMod.getProduct_id();

                    if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                        deleteFromCart(userId, prodid);

                    } else {
                        Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            itemRowHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(itemRowHolder.cart_item_number.getText().toString());

                    if (count > 1) {
                        count = count - 1;

                        itemRowHolder.cart_item_number.setText(String.valueOf(count));
                        String pluscount = String.valueOf(count);
                        String prodid= catMod.getProduct_id();
                        String userid = pref.get(Constants.USERID);
                        if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                            updateQty(pluscount, prodid, userid);

                        } else {
                            Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String userId = pref.get(Constants.USERID);
                        String prodid = catMod.getProduct_id();

                        if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                            deleteFromCart(userId, prodid);

                        } else {
                            Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            });
            itemRowHolder.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //updateQty();

                    int count = Integer.parseInt(itemRowHolder.cart_item_number.getText().toString());
                    count = count + 1;
                    itemRowHolder.cart_item_number.setText(String.valueOf(count));
                    Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                    //vibe.vibrate(100);
                    itemRowHolder.cart_item_number.setText(String.valueOf(count));
                    String pluscount = String.valueOf(count);
                    String prodid = catMod.getProduct_id();
                    String userid = pref.get(Constants.USERID);

                    if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                        updateQty(pluscount,  prodid, userid);

                    } else {
                        Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                    }

                }
            });

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

        public void updateQty(final String pluscount, final String prodid, final String userid) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.UPDATEQYT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("testttttttt", response);
                    try {
                        //converting the string to json array object
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                            String mgs = jsonObject.getString("message");
                             Toast.makeText(mContext,mgs,Toast.LENGTH_LONG).show();

                            if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                                getCart();

                            } else {
                                Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                             layout_cart_empty.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext,
                                    jsonObject.getString("message"),
                                    Toast.LENGTH_LONG).show();
                        }

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
                    params.put("qty", pluscount);
                    params.put("product_id",  prodid);

                    Log.e("", "" + params);
                    return params;
                }
            };

            //adding our stringrequest to queue
            Volley.newRequestQueue(mContext).add(stringRequest);
        }


        public void deleteFromCart(final String userid, final String prodid) {
            //loader.setMessage("Loading...Please Wait..");
              loader.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.DELETEPRODUCT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    loader.dismiss();

                    try {
                        //converting the string to json array object
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                            String mgs = jsonObject.getString("message");
                            Toast.makeText(mContext, mgs, Toast.LENGTH_LONG).show();
                            if (Utils.isNetworkConnectedMainThred(CartListActivity.this)) {
                                getCart();

                            } else {
                                Toast.makeText(CartListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // layout_cart_empty.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext,
                                    jsonObject.getString("message"),
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
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", userid);
                    params.put("product_id", prodid);

                    Log.e("deleteee", "" + params);
                    return params;
                }
            };

            //adding our stringrequest to queue
            Volley.newRequestQueue(mContext).add(stringRequest);
        }

    }
}