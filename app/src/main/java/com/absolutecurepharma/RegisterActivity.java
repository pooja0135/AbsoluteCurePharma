package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageView ivBack;
    Button btnSignup;
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //findViewById
        ivBack=findViewById(R.id.ivBack);
        btnSignup=findViewById(R.id.btnSignup);
        tvLogin=findViewById(R.id.tvLogin);

        //setOnClickListener
        ivBack.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        tvLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignup:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.ivBack:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.tvLogin:
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