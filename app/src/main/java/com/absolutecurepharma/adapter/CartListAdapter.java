package com.absolutecurepharma.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public abstract class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ItemRowHolder> {


    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;
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
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlusClick(v, String.valueOf(v.getTag()));
            }
        };
        minusclickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMinusClick(v, String.valueOf(v.getTag()));
            }
        };
        deletelickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick(v, String.valueOf(v.getTag()));
            }
        };
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
        CategoryModel catMod = categoryModel.get(i);

        itemRowHolder.tvSubcategory.setText(catMod.getProduct_id());


       // Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivSubcategory);

        itemRowHolder.ivDelete.setOnClickListener(deletelickListener);
        itemRowHolder.ivMinus.setOnClickListener(minusclickListener);
        itemRowHolder.ivPlus.setOnClickListener(clickListener);

    }


    @Override
    public int getItemCount() {


       return categoryModel.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvSubcategory;
        protected TextView tvDescription;

        protected ImageView ivSubcategory;
        protected ImageView ivDelete;
        protected ImageView ivMinus;
        protected ImageView ivPlus;



        public ItemRowHolder(View view) {
            super(view);

            this.tvSubcategory = view.findViewById(R.id.tvSubcategory);
            this.tvDescription = view.findViewById(R.id.tvDescription);
            this.ivSubcategory=view.findViewById(R.id.ivSubcategory);
            this.ivDelete=view.findViewById(R.id.ivDelete);
            this.ivMinus=view.findViewById(R.id.ivMinus);
            this.ivPlus=view.findViewById(R.id.ivPlus);


        }

    }

}