package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.absolutecurepharma.OrderDetailActivity;
import com.absolutecurepharma.R;
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

       // itemRowHolder.tvProductName.setText(categoryname[i]);
        //Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivProduct);
        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {


       return categoryModel.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvProductName;
        protected TextView tvDescription;

        protected ImageView ivProduct;



        public ItemRowHolder(View view) {
            super(view);

            this.tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            this.ivProduct=(ImageView)view.findViewById(R.id.ivProduct);


        }

    }

}