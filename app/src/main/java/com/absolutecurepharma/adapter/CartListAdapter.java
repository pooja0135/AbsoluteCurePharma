package com.absolutecurepharma.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.os.Vibrator;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.CartListActivity;
import com.absolutecurepharma.R;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ItemRowHolder> {


    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;
    CustomLoader loader;
//    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
//    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};
    public View.OnClickListener clickListener;
    public View.OnClickListener minusclickListener;
    public View.OnClickListener deletelickListener;
    protected abstract void onPlusClick(View view, String str);
    protected abstract void onMinusClick(View view, String str);
    protected abstract void onDeleteClick(View view, String str);


    public CartListAdapter(Context context, ArrayList categoryModel) {
        this.categoryModel = categoryModel;
        this.mContext = context;
//        clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onPlusClick(v, String.valueOf(v.getTag()));
//            }
//        };
//        minusclickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onMinusClick(v, String.valueOf(v.getTag()));
//            }
//        };
//        deletelickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onDeleteClick(v, String.valueOf(v.getTag()));
//            }
//        };
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        v.setOnClickListener(clickListener);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        CategoryModel catMod = categoryModel.get(i);

        itemRowHolder.tvOldPrice.setPaintFlags(itemRowHolder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        itemRowHolder.tvSubcategory.setText(catMod.getProduct_name());
        itemRowHolder.company.setText(catMod.getCompany());
        itemRowHolder.tvSize.setText(catMod.getSize());
        itemRowHolder.cart_item_number.setText(catMod.getQty());
        itemRowHolder.tvFinalprice.setText("\u20b9"+ catMod.getSelling_price());
        itemRowHolder.tvOldPrice.setText("\u20b9"+ catMod.getMarked_price());

        double discount= Double.parseDouble(catMod.getMarked_price())-Double.parseDouble(catMod.getSelling_price());
        double dis_percent=(discount*100)/Double.parseDouble(catMod.getMarked_price());
        DecimalFormat precision = new DecimalFormat("1");
        itemRowHolder.tvDiscount.setText(precision.format(dis_percent)+" % off");

        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getProduct_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.ivSubcategory);

       // Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivSubcategory);

        itemRowHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId="1";
                deleteFromCart(userId,Constants.product_id);
            }
        });
        itemRowHolder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(itemRowHolder.cart_item_number.getText().toString());

                if (count > 1) {
                    count=count-1;

                    itemRowHolder.cart_item_number.setText(String.valueOf(count));
                    String pluscount= String.valueOf(count);
                    Constants.product_id=catMod.getProduct_id();
                    String userid="1";
                    updateQty(pluscount,Constants.product_id,userid);

                }
                else {
//remove
                }
            }
        });
        itemRowHolder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateQty();

                int count = Integer.parseInt(itemRowHolder.cart_item_number.getText().toString());
                count=count+1;
                itemRowHolder.cart_item_number.setText(String.valueOf(count));
                Vibrator vibe = (Vibrator)mContext. getSystemService(Context.VIBRATOR_SERVICE);
                //vibe.vibrate(100);
                itemRowHolder.cart_item_number.setText(String.valueOf(count));
                String pluscount= String.valueOf(count);
                Constants.product_id=catMod.getProduct_id();
                String userid="1";
                updateQty(pluscount,Constants.product_id,userid);
            }
        });

    }


    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

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



        public ItemRowHolder(View view) {
            super(view);

            this.tvSubcategory = view.findViewById(R.id.tvSubcategoryname);
            this.tvDescription = view.findViewById(R.id.tvDescription);
            this.ivSubcategory=view.findViewById(R.id.ivSubcategory);
            this.ivDelete=view.findViewById(R.id.ivDelete);
            this.ivMinus=view.findViewById(R.id.ivMinus);
            this.ivPlus=view.findViewById(R.id.ivPlus);
            this.company=view.findViewById(R.id.tvCompanyName);
            this.tvSize=view.findViewById(R.id.tvSize);
            this.tvFinalprice=view.findViewById(R.id.tvFinalprice);
            this.tvOldPrice=view.findViewById(R.id.tvOldPrice);
            this.tvDiscount=view.findViewById(R.id.tvDiscount);
            this.cart_item_number=view.findViewById(R.id.cart_item_number);
           // this.layout_cart_empty=view.findViewById(R.id.layout_cart_empty);


        }



    }

    public void updateQty(final String pluscount,final String id,final String userid){
        //loader.setMessage("Loading...Please Wait..");
      //  loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.UPDATEQYT,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("success").equalsIgnoreCase("true")) {

                       String mgs= jsonObject.getString("message");
                       // Toast.makeText(mContext,mgs,Toast.LENGTH_LONG).show();

                    }
                    else {
                       // layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext,
                                jsonObject.getString("message"),
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
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",  userid);
                params.put("qty",  pluscount);
                params.put("product_id",Constants.product_id  );

                Log.e("", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(mContext).add(stringRequest);
    }


    public void deleteFromCart(final String id,final String userid){
        //loader.setMessage("Loading...Please Wait..");
        //  loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.DELETEPRODUCT,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("success").equalsIgnoreCase("true")) {

                        String mgs= jsonObject.getString("message");
                        // Toast.makeText(mContext,mgs,Toast.LENGTH_LONG).show();

                    }
                    else {
                        // layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext,
                                jsonObject.getString("message"),
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
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",  id);
                params.put("product_id",Constants.product_id  );

                Log.e("", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(mContext).add(stringRequest);
    }
}