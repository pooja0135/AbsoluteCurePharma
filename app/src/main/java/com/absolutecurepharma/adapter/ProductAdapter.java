package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.Model.CategoryModel;
import com.absolutecurepharma.ProductDetailActivity;
import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemRowHolder> {


    private Context mContext;
    int [] productimage={R.drawable.product_image1,R.drawable.product_image2,R.drawable.product_image3,R.drawable.product_image4};
    String[] productname={"Paracetamol","Borncorid","Cetaphil","Almond"};
    String[] productsize={"500mg","200ml","118ml","500gram",};

    public ProductAdapter(Context context, int[]productimage, String[]productname,String[]productsize) {
        this.productimage = productimage;
        this.productname = productname;
        this.productsize = productsize;
        this.mContext = context;
    }

    public ProductAdapter(Context context, ArrayList<CategoryModel> catlist) {
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {


        itemRowHolder.tvProductName.setText(productname[i]);
        itemRowHolder.tvSize.setText(productsize[i]);
        Picasso.with(mContext).load(productimage[i]).into(itemRowHolder.image);

        itemRowHolder.tvOldPrice.setPaintFlags(itemRowHolder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ProductDetailActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {


       return productimage.length;


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvProductName;
        protected TextView tvSize;
        protected TextView tvOldPrice;

        protected ImageView image;



        public ItemRowHolder(View view) {
            super(view);

            this.tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
            this.image=(ImageView)view.findViewById(R.id.ivProduct);


        }

    }

}