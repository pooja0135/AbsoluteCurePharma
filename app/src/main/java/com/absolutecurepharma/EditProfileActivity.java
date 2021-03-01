package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivityEditProfileBinding;
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

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
ActivityEditProfileBinding binding;
    CustomLoader loader;

    Preferences pref;
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    String name,contact,userid,email,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref=new Preferences(this);
        binding.edName.setText(pref.get(Constants.FULLNAME));
        binding.edEmail.setText(pref.get(Constants.EMAIL));
        binding.etContact.setText(pref.get(Constants.MOBILENUMBER));
        binding.tvSubmit.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tvSubmit:
                checkValidation();
                break;
            case R.id.ivBack:
                finish();
                break;
        }

    }

    private void checkValidation(){
        name=binding.edName.getText().toString();
        userid=pref.get(Constants.USERID);
        email=binding.edEmail.getText().toString();
        gender="male";


        if (!name.isEmpty() && !email.isEmpty() ) {

            if (Utils.isNetworkConnectedMainThred(EditProfileActivity.this)){

                editProfile(userid, name,email,gender);
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



    //****************************************************************//
    private void editProfile(final String userid, final String full_name,final String email,final String gender) {
        loader.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.EDITPROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Edit Profile" + response.toString());
                loader.dismiss();

                try {
                    JSONObject object = new JSONObject(response);

                    if(object.getString("success").equalsIgnoreCase("true")) {

                        Toast.makeText(getApplicationContext(), "Successfully Updated Profile!", Toast.LENGTH_LONG).show();
                        pref.set(Constants.FULLNAME,binding.edName.getText().toString());
                        pref.set(Constants.EMAIL, binding.edEmail.getText().toString());
                        pref.commit();
                        binding.edEmail.setText(pref.get(Constants.EMAIL));
                        binding.edName.setText(pref.get(Constants.FULLNAME));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "try later", Toast.LENGTH_LONG).show();
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
                Log.e("",""+error);
                loader.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userid);
                params.put("full_name", full_name);
                params.put("email",email);
                params.put("gender",gender);
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