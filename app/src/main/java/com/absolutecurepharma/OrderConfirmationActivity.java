package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.absolutecurepharma.databinding.ActivityOrderConfirmationBinding;

public class OrderConfirmationActivity extends AppCompatActivity {

    ActivityOrderConfirmationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
    }
}