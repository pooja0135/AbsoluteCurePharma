package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;

import com.Model.CategoryModel;
import com.absolutecurepharma.databinding.ActivityOrderDetailBinding;

public class OrderDetailActivity extends AppCompatActivity {

    ActivityOrderDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        Bundle data = getIntent().getExtras();
        CategoryModel model = (CategoryModel) data.getParcelable("order");
        Log.e("nishaaa",""+model.getOrder_id());


       // binding.tvOrderId.setText(model.getOrder_id());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OrderDetailActivity.this,OrderListActivity.class));


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,OrderListActivity.class));
    }
}