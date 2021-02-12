package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.SeverCall.Constants;
import com.absolutecurepharma.utils.Preferences;


public class SplashActivity extends AppCompatActivity {

    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        preferences=new Preferences(this);

        //  Log.e("IDDD",Constants.USERID);
        new Handler().postDelayed(new Runnable() {


            @Override public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));

                if (preferences.get(Constants.USERID).isEmpty()) {
                    // User is already logged in. Take him to main activity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                  finish();
            }
        }, 4000);



    }
}