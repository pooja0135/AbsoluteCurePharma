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

import com.absolutecurepharma.SeverCall.AppConfig;
import com.absolutecurepharma.customecomponent.CustomLoader;

import com.absolutecurepharma.databinding.ActivityRegisterBinding;
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
    ActivityRegisterBinding binding1;
    String name,email,mobile,password;
    CustomLoader loader;
    EditText et_name,et_email,et_mobile,et_password;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_register);
        binding1 = DataBindingUtil.setContentView(this, R.layout.activity_register);


        binding1 = DataBindingUtil.setContentView(this, R.layout.activity_register);


        Log.e(TAG,"nisha"+name);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


        //setOnClickListener
        binding1.ivBack.setOnClickListener(this);
        binding1.btnSignup.setOnClickListener(this);
        binding1.tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignup:

                name= binding1.etName.getText().toString();
                email=binding1.etEmail.getText().toString();
                mobile= binding1.etMobile.getText().toString();
                password=binding1.etPassword.getText().toString();
               // startActivity(new Intent(this,MainActivity.class));
                registerUser(name,email,mobile,password);
                break;
            case R.id.ivBack:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.tvLogin:
                startActivity(new Intent(this,LoginActivity.class));
                break;

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
                        Log.d(TAG, "Register Response: " + response.toString());
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
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
                params.put("Password",password);
                Log.e("SSSSSS","hejhd"+params);

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