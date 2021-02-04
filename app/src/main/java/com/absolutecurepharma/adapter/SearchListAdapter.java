package com.absolutecurepharma.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.absolutecurepharma.ProductDetailActivity;
import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public  class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ItemRowHolder> implements Filterable {


    private Context mContext;

    List<CategoryModel> mData;
    List<CategoryModel> mStringFilterList;
    ValueFilter valueFilter;
    private LayoutInflater inflater;
    String searchString="";


    public SearchListAdapter(Context context,List<CategoryModel> mData) {
        this.mContext = context;
        this.mData = mData;
        this.mStringFilterList = mData;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);

        return mh;
    }
    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int position) {


        itemRowHolder.tvSearch.setText(mData.get(position).getProduct_name());
        Picasso.with(mContext).load(AppConfig.IMAGE_PATH+mData.get(position).getProduct_image()).into(itemRowHolder.ivProduct);

        String search = mData.get(position).getProduct_name().toLowerCase(Locale.getDefault());
        if (search.contains(searchString)) {
            int startPos = search.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanText = Spannable.Factory.getInstance().newSpannable(itemRowHolder.tvSearch.getText()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
            spanText.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            itemRowHolder.tvSearch.setText(spanText, TextView.BufferType.SPANNABLE);
        }


        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class));
            }
        });


    }


    @Override
    public int getItemCount() {


       return mData.size();


    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvSearch;
        ImageView ivProduct;

        public ItemRowHolder(View view) {
            super(view);

            tvSearch = view.findViewById(R.id.tvSearch);
            ivProduct = view.findViewById(R.id.ivProduct);



        }

    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public void Filter(String searchString) {

        this.searchString = searchString;

        // Filtering stuff as normal.
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<CategoryModel> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getProduct_name().toUpperCase()).contains(constraint.toString().toUpperCase())) {

                        CategoryModel catModel = new CategoryModel();
                        //adding the product to product list
                        catModel.setProduct_name(mStringFilterList.get(i).getProduct_name());
                        catModel.setProduct_image(mStringFilterList.get(i).getProduct_image());
                        catModel.setProduct_id(mStringFilterList.get(i).getProduct_id());
                        filterList.add(catModel);
                       // filterList.add(mStringFilterList.get(i).getProduct_name());
                    }
                }


                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (List<CategoryModel>) results.values;

            notifyDataSetChanged();
        }

    }

}