package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.OrderDetailActivity;
import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

public  class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemRowHolder> {


    private Context mContext;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};


    public OrderListAdapter(Context context, int[]categoryimage, String[]categoryname) {
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
        this.mContext = context;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderplaced_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {


        itemRowHolder.tvProductName.setText(categoryname[i]);
        Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivProduct);
        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetailActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {


       return categoryimage.length;


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