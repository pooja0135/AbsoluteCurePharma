package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.SeverCall.AppConfig;
import com.absolutecurepharma.customecomponent.CustomLoader;


import com.absolutecurepharma.databinding.ActivityRegisterBinding;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = RegisterActivity.class.getSimpleName();
    ActivityRegisterBinding binding;
    String name,email,mobile,password;
    CustomLoader loader;
 Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
         pref=new Preferences(this);

        //setOnClickListener
        binding.ivBack.setOnClickListener(this);
        binding.btnSignup.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignup:
                vaidation();
                break;
            case R.id.ivBack:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.tvLogin:
                startActivity(new Intent(this,LoginActivity.class));
                break;

        }

    }

    private void vaidation(){
        name= binding.etName.getText().toString();
        email=binding.etEmail.getText().toString();
        mobile= binding.etMobile.getText().toString();
        password=binding.etPassword.getText().toString();

        if (!name.isEmpty() && !password.isEmpty()&& !mobile.isEmpty() && !email.isEmpty()) {

            if (Utils.isNetworkConnectedMainThred(RegisterActivity.this)){
                registerUser(name,email,mobile, password);
            } else {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }



//***************************************************************************//

    private void registerUser(final String name,final String email,final String mobile,final String password) {
        loader.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                loader.dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);

                    if(jObj.getString("success_msg").equalsIgnoreCase("1")) {

                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        Toast.makeText(getApplicationContext(), "Registration Successfull! Please Login Now", Toast.LENGTH_LONG).show();
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
                params.put("full_name", name);
                params.put("email", email);
                params.put("mobile_no",mobile);
                params.put("password",password);
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