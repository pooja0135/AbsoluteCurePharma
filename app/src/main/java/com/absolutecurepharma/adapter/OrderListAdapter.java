package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.OrderDetailActivity;
import com.absolutecurepharma.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public  class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemRowHolder> {


    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;


    public OrderListAdapter(Context context,  ArrayList CategoryModel) {

        this.mContext = context;
        this.categoryModel = CategoryModel;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderplaced_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        CategoryModel catMod = categoryModel.get(i);
        itemRowHolder.tvProductName.setText(catMod.getProduct_name());
        itemRowHolder.tvcompanyname.setText(catMod.getCompany());
        itemRowHolder.tvOrderDate.setText("Ordered on" + " " +catMod.getOrder_date());

       // itemRowHolder.tvProductName.setText(categoryname[i]);
        //Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivProduct);
        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, OrderDetailActivity.class);
               // intent.putExtra("order", new CategoryModel(catMod.getProduct_name(), catMod.getMarked_price(), catMod.getSelling_price(), catMod.getProduct_image(), catMod.getOrder_id(), catMod.getOrder_total(),catMod.getOrder_date(), catMod.getOrder_status(), catMod.getDelivery_date(), catMod.getProduct_qty(),catMod.getTotal_price()));
                intent.putExtra("order", catMod);
                mContext.startActivity(intent);
            }
        });
        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getProduct_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.ivProduct);

    }


    @Override
    public int getItemCount() {


       return categoryModel.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvProductName;
        protected TextView tvDescription;
        protected TextView tvcompanyname;
        protected TextView tvOrderDate;

        protected ImageView ivProduct;



        public ItemRowHolder(View view) {
            super(view);

            this.tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            this.tvcompanyname = (TextView) view.findViewById(R.id.tvCompanyName);
            this.tvOrderDate = (TextView) view.findViewById(R.id.tvOrderDate);
            this.ivProduct=(ImageView)view.findViewById(R.id.ivProduct);


        }

    }

}