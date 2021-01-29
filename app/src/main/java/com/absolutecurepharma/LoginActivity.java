package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    ImageView ivBack;
    TextView tvForgotPassword,tvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnLogin);
        ivBack=findViewById(R.id.ivBack);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);
        tvSignUp=findViewById(R.id.tvSignUp);

        //setOnClickListener
        btnLogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogin:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.ivBack:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(this,ForgotPasswordActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}