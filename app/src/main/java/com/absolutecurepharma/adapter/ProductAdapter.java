package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.ProductDetailActivity;
import com.absolutecurepharma.R;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemRowHolder> {


    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;
//    int [] productimage={R.drawable.product_image1,R.drawable.product_image2,R.drawable.product_image3,R.drawable.product_image4};
//    String[] productname={"Paracetamol","Borncorid","Cetaphil","Almond"};
//    String[] productsize={"500mg","200ml","118ml","500gram",};

//    public ProductAdapter(Context context, int[]productimage, String[]productname,String[]productsize) {
//        this.productimage = productimage;
//        this.productname = productname;
//        this.productsize = productsize;
//        this.mContext = context;
//    }

    public ProductAdapter(Context context, ArrayList<CategoryModel> catlist) {
        mContext=context;
        categoryModel=catlist;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_list_items, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        CategoryModel catMod = categoryModel.get(i);
        itemRowHolder.tvProductName.setText(catMod.getProduct_name());
        itemRowHolder.tvSize.setText(catMod.getSize());
        itemRowHolder.tvOldPrice.setText("\u20b9"+ catMod.getMarked_price());
        itemRowHolder.tvFinalPrice.setText("\u20b9"+ catMod.getSelling_price());

        double discount= Double.parseDouble(catMod.getMarked_price())-Double.parseDouble(catMod.getSelling_price());
        double dis_percent=(discount*100)/Double.parseDouble(catMod.getMarked_price());
        DecimalFormat precision = new DecimalFormat("0");
        itemRowHolder.tvDiscount.setText(precision.format(dis_percent)+" % off");

        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getProduct_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.image);


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


        return categoryModel.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvProductName;
        protected TextView tvSize;
        protected TextView tvOldPrice;
        protected TextView tvFinalPrice;
        protected TextView tvDiscount;

        protected ImageView image;



        public ItemRowHolder(View view) {
            super(view);

            this.tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            this.tvSize = (TextView) view.findViewById(R.id.tvSize);
            this.tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
            this.tvFinalPrice = (TextView) view.findViewById(R.id.tvFinalprice);
            this.image=(ImageView)view.findViewById(R.id.ivProduct);
            this.tvDiscount=(TextView)view.findViewById(R.id.tvDiscount);


        }

    }

}