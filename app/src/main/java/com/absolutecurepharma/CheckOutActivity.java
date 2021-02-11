package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    TextView tvChange,tvProceed;
    RecyclerView recyclerview;
   // CartListAdapter cartListAdapter;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &Supplements"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        //findViewById
        ivBack=findViewById(R.id.ivBack);
        tvChange=findViewById(R.id.tvChange);
        tvProceed=findViewById(R.id.tvProceed);
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        cartListAdapter= new CartListAdapter(this, categoryimage, categoryname) {
//
//            @Override
//            protected void onPlusClick(View view, String str) {
//
//            }
//
//            @Override
//            protected void onMinusClick(View view, String str) {
//
//            }
//
//            @Override
//            protected void onDeleteClick(View view, String str) {
//
//            }
//        };

      //  recyclerview.setAdapter(cartListAdapter);


        //setonCLicklistener
        ivBack.setOnClickListener(this);
        tvChange.setOnClickListener(this);
        tvProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvProceed:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}