package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.absolutecurepharma.adapter.OrderListAdapter;
import com.absolutecurepharma.databinding.ActivityOrderListBinding;
import com.absolutecurepharma.databinding.ActivityRegisterBinding;

public class OrderListActivity extends AppCompatActivity {
    ActivityOrderListBinding binding;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin & Supplements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);



        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderListActivity.this,MainActivity.class));
            }
        });

        binding.rvOrder.setLayoutManager(new GridLayoutManager(this, 1));
        binding.rvOrder.setAdapter(new OrderListAdapter(this,categoryimage,categoryname));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}