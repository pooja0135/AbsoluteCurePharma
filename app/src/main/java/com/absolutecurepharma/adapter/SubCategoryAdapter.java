package com.absolutecurepharma.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

public abstract class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ItemRowHolder> {


    private Context mContext;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};
    public View.OnClickListener clickListener;
    protected abstract void onSubCategoryClick(View view, String str);


    public SubCategoryAdapter(Context context, int[]categoryimage, String[]categoryname) {
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
        this.mContext = context;
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubCategoryClick(v, String.valueOf(v.getTag()));
            }
        };
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subcat_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        v.setOnClickListener(clickListener);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {


        itemRowHolder.tvSubcategory.setText(categoryname[i]);


        Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.ivSubcategory);


    }


    @Override
    public int getItemCount() {


       return categoryimage.length;


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvSubcategory;
        protected TextView tvDescription;

        protected ImageView ivSubcategory;



        public ItemRowHolder(View view) {
            super(view);

            this.tvSubcategory = (TextView) view.findViewById(R.id.tvSubcategory);
            this.tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            this.ivSubcategory=(ImageView)view.findViewById(R.id.ivSubcategory);


        }

    }

}