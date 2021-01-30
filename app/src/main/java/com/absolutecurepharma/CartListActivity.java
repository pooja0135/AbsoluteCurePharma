package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.absolutecurepharma.adapter.CartListAdapter;
import com.absolutecurepharma.adapter.ProductListAdapter;
import com.absolutecurepharma.fragment.ProductListFragment;

public class CartListActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvCart;
    CartListAdapter cartListAdapter;
    LinearLayout llcartItem;

    //Textview
    TextView tvHeaderText;
    TextView tvProceed;
    TextView tvFinalprice;

    Button bAddNew;
    ImageView ivBack;
    LinearLayout layout_cart_empty;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &Supplements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        llcartItem = findViewById(R.id.llcartItem);
        bAddNew = findViewById(R.id.bAddNew);
        ivBack = findViewById(R.id.ivBack);
        rvCart = findViewById(R.id.rvCart);
        tvFinalprice = findViewById(R.id.tvFinalprice);
        tvProceed = findViewById(R.id.tvProceed);

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartListAdapter= new CartListAdapter(this, categoryimage, categoryname) {

            @Override
            protected void onPlusClick(View view, String str) {

            }

            @Override
            protected void onMinusClick(View view, String str) {

            }

            @Override
            protected void onDeleteClick(View view, String str) {

            }
        };

        rvCart.setAdapter(cartListAdapter);

        //setonCLicklistener
        ivBack.setOnClickListener(this);
        bAddNew.setOnClickListener(this);
        tvProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;

            case R.id.bAddNew:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tvProceed:
                startActivity(new Intent(this,CheckOutActivity.class));
                break;
        }
    }
}