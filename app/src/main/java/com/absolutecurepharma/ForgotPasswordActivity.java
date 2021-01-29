package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    TextView tvSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        ivBack=findViewById(R.id.ivBack);
        tvSubmit=findViewById(R.id.tvSubmit);


        //setOnClicklistener
        ivBack.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      switch (v.getId())
      {
          case R.id.ivBack:
              startActivity(new Intent(this,LoginActivity.class));
              break;
          case R.id.tvSubmit:
              startActivity(new Intent(this,LoginActivity.class));
              break;
      }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginActivity.class));
    }
}