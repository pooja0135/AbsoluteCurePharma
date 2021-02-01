package com.absolutecurepharma.adapter;


import android.content.Context;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;


import com.absolutecurepharma.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;



public abstract class CardPagerAdapter extends PagerAdapter  {

    private Context mContext;
    private float mBaseElevation;
    public View.OnClickListener clickListener;
    ArrayList<HashMap<String, String>> arraylist;
    int [] array;

    protected abstract void onBannerClick(View view, String str);


    public CardPagerAdapter(Context context,  int [] array) {
        mContext = context;
        this.array=array;
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBannerClick(v, String.valueOf(v.getTag()));
            }
        };
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_adapter, container, false);
        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        if (this.mBaseElevation == 0.0f) {
            this.mBaseElevation = cardView.getCardElevation();
        }

        Picasso.with(this.mContext).load(array[position]).into(iv);
        cardView.setMaxCardElevation(this.mBaseElevation * ((float) this.array.length));

        view.setTag(position);
        view.setOnClickListener(clickListener);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }



    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



}
