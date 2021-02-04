package com.absolutecurepharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.absolutecurepharma.SeverCall.AppConfig;
import com.absolutecurepharma.SeverCall.Constants;

import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivityLoginBinding;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {
    ActivityLoginBinding binding;
    String token,userid,password;
    CustomLoader loader;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

      //  loader=new ProgressDialog(this);



      binding.btnLogin.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
        binding.tvSignUp.setOnClickListener(this);
        binding.tvForgotPassword.setOnClickListener(this);

//PrefManager.setStringPreference(this, Constants.USERID,"1");



         //generate token
        getFCM_token();
        //loginUser();
    }


    public void getFCM_token()
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {

                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Log", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        // Log and toast
                        String  msg = token;
                        Log.e("msg", msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();


                    }
                });
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btnLogin:
            {
                //servercall
                userid=  binding.etMobile.getText().toString();
                password=  binding.etPassword.getText().toString();
                loginUser(userid,password);
                break;
            }
            case R.id.ivBack:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tvForgotPassword:
             //   startActivity(new Intent(this,ForgotPasswordActivity.class));
                break;
        }
    }


    //****************************************************************//
    private void loginUser(final String userid, final String password) {
        loader.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                loader.dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);

                    if(jObj.getString("Success").equalsIgnoreCase("true")) {


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "wrong credentials", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userid);
                params.put("password", password);
                params.put("fcm_token",token);
                Log.e("",""+params);

                return params;
            }

        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(strReq);
    }
}