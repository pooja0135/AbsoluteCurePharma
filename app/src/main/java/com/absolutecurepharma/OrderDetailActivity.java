package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;

import com.Model.CategoryModel;
import com.SeverCall.Constants;
import com.absolutecurepharma.databinding.ActivityOrderDetailBinding;
import com.absolutecurepharma.utils.Preferences;

public class OrderDetailActivity extends AppCompatActivity {

    ActivityOrderDetailBinding binding;
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        Bundle data = getIntent().getExtras();
        CategoryModel model =  data.getParcelable("order");
        Log.e("nishaaa",""+model.getProduct_name());
        pref=new Preferences(this);
        binding.tvOrderId.setText(model.getOrder_id());
        binding.tvOrderDate.setText(model.getOrder_date());
        binding.tvProductname.setText(model.getProduct_name());
        binding.tvOrderTotal.setText("\u20B9"+model.getOrder_total());
       binding.tvName.setText(pref.get(Constants.FULLNAME));
       binding.tvAddress.setText(pref.get(Constants.ADDRESS));
       binding.tvPhone.setText(pref.get(Constants.MOBILENUMBER));
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