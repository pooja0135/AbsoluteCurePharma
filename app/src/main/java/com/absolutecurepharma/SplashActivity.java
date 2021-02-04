package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;



public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        //  Log.e("IDDD",Constants.USERID);
        new Handler().postDelayed(new Runnable() {


            @Override public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                // This method will be executed once the timer is over
//                if (prefManager.getStringPreference(SplashActivity.this, "userid").equalsIgnoreCase(" ") || prefManager.getStringPreference(SplashActivity.this, "userid") != null ) {
//                    // User is already logged in. Take him to main activity
//                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else {
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
                //  finish();
            }
        }, 4000);



    }
}