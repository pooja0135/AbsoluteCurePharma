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


import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.R;


import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRowHolder> {


    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;
    public View.OnClickListener clickListener;


    //int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};

    public CategoryAdapter(Context context,ArrayList CategoryModel) {

        this.mContext = context;
        this.categoryModel = CategoryModel;
    }

    protected abstract void onCategoryClick(View view, String str);



//    public CategoryAdapter(Context context, int[]categoryimage,String[]categoryname) {
//        this.categoryimage = categoryimage;
//        this.categoryname = categoryname;
//        this.mContext = context;
//        clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onCategoryClick(v, String.valueOf(v.getTag()));
//            }
//        };
//    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        v.setOnClickListener(clickListener);
        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        CategoryModel catMod = categoryModel.get(i);

        //  itemRowHolder.tvCatName.setText(categoryname[i]);
        itemRowHolder.tvCatId.setText(catMod.getCat_id());
        itemRowHolder.tvCatName.setText(catMod.getCat_name());

        String image_url= AppConfig.IMAGE_PATH +categoryModel.get(i).getCat_image();
        // Picasso.with(mContext).load(categoryModel.get(i).getCat_image()).fit().centerCrop().into(itemRowHolder.image);
        Glide.with(mContext)
                .load(image_url)
                .into(itemRowHolder.image);


    }


    @Override
    public int getItemCount() {


        return categoryModel.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvCatName;
        protected TextView tvCatId;

        protected ImageView image;



        public ItemRowHolder(View view) {
            super(view);

            this.tvCatName = (TextView) view.findViewById(R.id.tvCatName);
            this.tvCatId = (TextView) view.findViewById(R.id.tvCatName);
            this.image=(ImageView)view.findViewById(R.id.image);


        }

    }

}