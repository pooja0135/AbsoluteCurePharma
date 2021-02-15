package com.absolutecurepharma.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.ProductDetailActivity;
import com.absolutecurepharma.R;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ItemRowHolder> {


    private Context mContext;
    public View.OnClickListener clickListener;
    private ArrayList<CategoryModel> categoryModel;
    CustomLoader loader;
    String finalPrice,totalPrice,prodId;
    Preferences pref;
    protected abstract void onSubCategoryClick(View view, String str);


    public SubCategoryAdapter(Context context, ArrayList CategoryModel) {
        this.categoryModel = CategoryModel;
        this.mContext = context;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subcat_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref=new Preferences(mContext);
        CategoryModel catMod = categoryModel.get(i);
        itemRowHolder.tvSubcategory.setText(catMod.getProduct_name());
        itemRowHolder.tvSize.setText(catMod.getSize());
        itemRowHolder.tvDescription.setText(catMod.getDescription());
        itemRowHolder.tvFinalprice.setText("\u20b9"+ catMod.getSelling_price());
        itemRowHolder.tvOldPrice.setText("\u20b9"+ catMod.getMarked_price());
        double discount= Double.parseDouble(catMod.getMarked_price())-Double.parseDouble(catMod.getSelling_price());
        double dis_percent=(discount*100)/Double.parseDouble(catMod.getMarked_price());
        DecimalFormat precision = new DecimalFormat("0");
        itemRowHolder.tvDiscount.setText(precision.format(dis_percent)+" % off");

        finalPrice=catMod.getSelling_price();
        totalPrice=catMod.getSelling_price();
        prodId=Constants.product_id;

        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getProduct_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.ivSubcategory);


        itemRowHolder.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnectedMainThred(mContext)) {
                    prodId=catMod.getProduct_id();
                   addToCart(prodId,finalPrice,totalPrice);
                } else {
                    Toast.makeText(mContext, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                }
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
        protected TextView tvSize;
        protected TextView tvFinalprice;
        protected TextView tvOldPrice;
        protected TextView tvDiscount;
        protected TextView tvAddToCart;
        protected ImageView ivSubcategory;

        public ItemRowHolder(View view) {
            super(view);

            this.tvSubcategory = (TextView) view.findViewById(R.id.tvSubcategory);
            this.tvDescription = (TextView) view.findViewById(R.id.tvCompanyName);
            this.ivSubcategory=(ImageView)view.findViewById(R.id.ivSubcategory);
            this.tvSize=(TextView)view.findViewById(R.id.tvSize);
            this.tvFinalprice=(TextView)view.findViewById(R.id.tvFinalprice);
            this.tvOldPrice=(TextView)view.findViewById(R.id.tvOldPrice);
            this.tvDiscount=(TextView)view.findViewById(R.id.tvDiscount);
            this.tvAddToCart=(TextView)view.findViewById(R.id.tvAddToCart);
        }
    }
    public void addToCart(final String prodId,final String sellingPrice, final String totalPrice){
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
                        Toast.makeText(mContext,
                                jsonObject.getString("success_msg"),
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(mContext,
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
                params.put("prod_id", prodId) ;
                params.put("user_id",pref.get(Constants.USERID)) ;
                params.put("qty","1") ;
                params.put("price",sellingPrice) ;
                params.put("total",totalPrice) ;

                Log.e("NEW BHAGYA",""+params);
                return params;
            }
        };;
        //adding our stringrequest to queue
        Volley.newRequestQueue(mContext).add(stringRequest);
    }

}