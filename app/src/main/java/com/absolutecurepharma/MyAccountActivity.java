package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.SeverCall.Constants;
import com.absolutecurepharma.databinding.ActivityMyAccountBinding;
import com.absolutecurepharma.utils.Preferences;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMyAccountBinding binding;
    Preferences pref;
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
        pref=new Preferences(this);
        binding.tvName.setText(pref.get(Constants.FULLNAME));
        binding.tvPhone.setText(pref.get(Constants.MOBILENUMBER));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                startActivity(new Intent(MyAccountActivity.this,MainActivity.class));
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MyAccountActivity.this,MainActivity.class));
    }
}