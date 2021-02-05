package com.absolutecurepharma.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public abstract class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ItemRowHolder> {


    private Context mContext;
    public View.OnClickListener clickListener;
    private ArrayList<CategoryModel> categoryModel;
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

        Log.e("shityal","shital");
        CategoryModel catMod = categoryModel.get(i);
        itemRowHolder.tvSubcategory.setText(catMod.getProduct_name());
        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getProduct_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.ivSubcategory);
    }


    @Override
    public int getItemCount() {


      return categoryModel.size();


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