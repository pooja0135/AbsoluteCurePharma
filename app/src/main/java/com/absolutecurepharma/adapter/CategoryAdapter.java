package com.absolutecurepharma.adapter;


import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.LoginActivity;
import com.absolutecurepharma.R;


import com.absolutecurepharma.fragment.SubcategoryFragment;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRowHolder> {
    private Context mContext;
    private ArrayList<CategoryModel> categoryModel;

    public CategoryAdapter(Context context, ArrayList CategoryModel) {

        this.mContext = context;
        this.categoryModel = CategoryModel;
    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        CategoryModel catMod = categoryModel.get(i);
        itemRowHolder.llnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.cat_id=catMod.getCat_id();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                SubcategoryFragment myFragment = new SubcategoryFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, myFragment).addToBackStack(null).commit();

            }
        });

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
        protected LinearLayout llnew;

        public ItemRowHolder(View view) {
            super(view);
            this.tvCatName = (TextView) view.findViewById(R.id.tvCatName);
            this.tvCatId = (TextView) view.findViewById(R.id.tvCatName);
            this.image=(ImageView)view.findViewById(R.id.image);
            this.llnew=(LinearLayout) view.findViewById(R.id.llnew);
        }
    }



}