package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.absolutecurepharma.databinding.ActivityMyAccountBinding;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMyAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_account);

        binding.ivBack.setOnClickListener(this);
        binding.rlEdit.setOnClickListener(this);
        binding.rlOrder.setOnClickListener(this);
        binding.rlAddress.setOnClickListener(this);
        binding.rlChangePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlEdit:
                startActivity(new Intent(MyAccountActivity.this,EditProfileActivity.class));
                break;
            case R.id.rlOrder:
                startActivity(new Intent(MyAccountActivity.this,OrderListActivity.class));
                break;
            case R.id.rlAddress:
                startActivity(new Intent(MyAccountActivity.this,AddressListActivity.class));
                break;
            case R.id.rlChangePassword:
                startActivity(new Intent(MyAccountActivity.this,ChangePasswordActivity.class));
                break;
        }
    }
}