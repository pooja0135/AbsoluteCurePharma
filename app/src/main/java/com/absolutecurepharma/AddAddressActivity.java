package com.absolutecurepharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Model.AddressItem;
import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.adapter.CategoryAdapter;
import com.absolutecurepharma.customecomponent.CustomLoader;

import com.absolutecurepharma.databinding.ActivityAddAddressBinding;
import com.absolutecurepharma.utils.Model;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {


    ActivityAddAddressBinding binding;

    CustomLoader loader;

    Preferences pref;


    ArrayList<HashMap<String,String>> arrayList;
    ArrayList<HashMap<String,String>> arrayCity;

    private static final String TAG = AddAddressActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address);


        arrayList=new ArrayList<>();
        arrayCity=new ArrayList<>();

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        pref=new Preferences(this);


        binding.spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (Utils.isNetworkConnectedMainThred(AddAddressActivity.this)){
                    getCity(arrayList.get(position).get("state_id"));
                } else {
                    Toast.makeText(AddAddressActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (Utils.isNetworkConnectedMainThred(AddAddressActivity.this)){
            getState();
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAddressActivity.this,AddressListActivity.class));
            }
        });

    }
    public void validation()
    {
        if(binding.edName.getText().toString().trim().isEmpty())
        {
            binding.edName.setError("This field can not be blank");

        }
        else if(binding.edMobile.getText().toString().trim().isEmpty()||binding.edMobile.getText().toString().trim().length()<10)
        {
            binding.edMobile.setError("Enter 10 digit phone number");
        }
        else if(binding.edLocality.getText().toString().trim().isEmpty())
            {
                binding.edLocality.setError("This field can not be blank");
                 }
        else if(binding.edAddress.getText().toString().trim().isEmpty())
        {
            binding.edAddress.setError("This field can not be blank");
        }

        else if(binding.edPincode.getText().toString().trim().isEmpty())
        {
            binding.edPincode.setError("This field can not be blank");
        }
        else
        {
            if (Utils.isNetworkConnectedMainThred(AddAddressActivity.this)){
                AddAddressAPI(pref.get(Constants.USERID),binding.edName.getText().toString(),arrayList.get(binding.spinnerState.getSelectedItemPosition()).get("state_id"),arrayCity.get(binding.spinnerCity.getSelectedItemPosition()).get("city_id").toString(),binding.edMobile.getText().toString(),binding.edLocality.getText().toString(),binding.edAddress.getText().toString(),binding.edPincode.getText().toString());
            } else {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SpinnerAdapter extends ArrayAdapter<HashMap<String, String>> {

        ArrayList<HashMap<String, String>> list;

        public SpinnerAdapter(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> list) {

            super(context, textViewResourceId, list);

            this.list = list;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.spinner_layout, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvName);
            //label.setTypeface(typeface3);
            label.setText(list.get(position).get("key"));
            return row;
        }
    }


    public void getState(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.GETSTATE,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    HashMap<String,String> map;

                    if(jsonObject.getString("Success").equalsIgnoreCase("true")) {
                        JSONArray array = jsonObject.getJSONArray("State");
                        {


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                map=new HashMap<>();

                                //getting product object from json array
                                JSONObject cat = array.getJSONObject(i);
                                 map.put("state_id",cat.getString("state_id"));
                                 map.put("key",cat.getString("state_name"));
                                 arrayList.add(map);
                            }

                            binding.spinnerState.setAdapter(new SpinnerAdapter(AddAddressActivity.this,R.layout.spinner_adapter,arrayList));

                        }
                    }
                    else {
                    }
                    //creating adapter object and setting it to recyclerview
                }
                catch (JSONException e) {
                    Log.e("testerroor",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //adding our stringrequest to queue
        Volley.newRequestQueue(AddAddressActivity.this).add(stringRequest);
    }
    public void getCity(String state_id){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GETCITY,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Getcity", response);

                loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);
                    arrayCity.clear();

                    if(jsonObject.getString("Success").equalsIgnoreCase("true")) {

                        JSONArray array = jsonObject.getJSONArray("City");
                        {


                            HashMap<String,String> map;
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                map=new HashMap<>();

                                //getting product object from json array
                                JSONObject cat = array.getJSONObject(i);
                                map.put("city_id",cat.getString("city_id"));
                                map.put("key",cat.getString("city_name"));
                                arrayCity.add(map);
                            }

                            binding.spinnerCity.setAdapter(new SpinnerAdapter(AddAddressActivity.this,R.layout.spinner_adapter,arrayCity));

                        }
                    }
                    else {
                        Toast.makeText(AddAddressActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {

                    Log.e("testerroor",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("state_id",state_id);
                Log.e("",""+params);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


//***************************************************************************//
//***************************************************************************//
    private void AddAddressAPI(final String customer_id,final String deliver_to,final String state_id,final String city_id,final String mobile_no,final String address1,final String address2,final String pin_code ) {
        loader.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.ADD_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Address Response: " + response.toString());
                loader.dismiss();
                try {
                    JSONObject jObj = new JSONObject(response);

                    if(jObj.getString("Success").equalsIgnoreCase("true")) {

                        startActivity(new Intent(AddAddressActivity.this,AddressListActivity.class));
                        Toast.makeText(getApplicationContext(), "Address added Sucessfully", Toast.LENGTH_LONG).show();
                    }
                    else {
                       // Toast.makeText(getApplicationContext(), "wrong credentials", Toast.LENGTH_LONG).show();
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
                params.put("customer_id", customer_id);
                params.put("deliver_to", deliver_to);
                params.put("state_id",state_id);
                params.put("city_id",city_id);
                params.put("mobile_no",mobile_no);
                params.put("address1",address1);
                params.put("address2",address2);
                params.put("pin_code",pin_code);
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