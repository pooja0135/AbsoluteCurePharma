package com.absolutecurepharma.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public abstract class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRowHolder> {


    private Context mContext;
    public View.OnClickListener clickListener;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};

    protected abstract void onCategoryClick(View view, String str);



    public CategoryAdapter(Context context, int[]categoryimage,String[]categoryname) {
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
        this.mContext = context;
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCategoryClick(v, String.valueOf(v.getTag()));
            }
        };
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        v.setOnClickListener(clickListener);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {


        itemRowHolder.tvCatName.setText(categoryname[i]);


        Picasso.with(mContext).load(categoryimage[i]).fit().centerCrop().into(itemRowHolder.image);


    }


    @Override
    public int getItemCount() {


       return categoryimage.length;


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvCatName;
        protected TextView tvPlace;

        protected ImageView image;



        public ItemRowHolder(View view) {
            super(view);

            this.tvCatName = (TextView) view.findViewById(R.id.tvCatName);
            this.image=(ImageView)view.findViewById(R.id.image);


        }

    }

}