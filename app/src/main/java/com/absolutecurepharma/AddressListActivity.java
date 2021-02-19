package com.absolutecurepharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.Model.AddressItem;
import com.Model.CategoryModel;
import com.SeverCall.AppConfig;
import com.SeverCall.Constants;
import com.absolutecurepharma.customecomponent.CustomLoader;
import com.absolutecurepharma.databinding.ActivityAddressListBinding;
import com.absolutecurepharma.utils.Preferences;
import com.absolutecurepharma.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddressListActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddressListBinding binding;
    ArrayList<AddressItem> addresslist;
    CustomLoader loader;
    AddressAdapter addressAdapter;
    Preferences pref;
    private int row_index = -1;
    String areaId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_list);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(this,1));
       // binding.recyclerview.setAdapter(new AddressAdapter(addresslist));
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        pref=new Preferences(this);
        addresslist=new ArrayList<>();

        if (Utils.isNetworkConnectedMainThred(this)) {
            getAddress();
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
        //setOnClicklistener
        binding.ivBack.setOnClickListener(this);
        binding.tvAddAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvAddAddress:
                startActivity(new Intent(this,AddAddressActivity.class));
                break;
        }
    }


    class AddressAdapter extends RecyclerView.Adapter<AddressListActivity.AddressAdapter.Holder> {

        private Context mContext;
        private ArrayList<AddressItem> addrmodel;
        CustomLoader loader;
        public AddressAdapter(Context context, ArrayList addrmodel) {
            this.addrmodel = addrmodel;
            this.mContext = context;
        }

        @Override
        public AddressListActivity.AddressAdapter.Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_items, null);
            AddressListActivity.AddressAdapter.Holder mh = new AddressListActivity.AddressAdapter.Holder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(AddressListActivity.AddressAdapter.Holder itemRowHolder, int i) {
            AddressItem addr = addrmodel.get(i);

            if(row_index==i)
            {
                String addrid = addr.getId();
                itemRowHolder.ivradioOn.setVisibility(View.VISIBLE);
                itemRowHolder.ivradio.setVisibility(View.GONE);
            }
            else
            {
                String addrid = addr.getId();
                itemRowHolder.ivradioOn.setVisibility(View.GONE);
                itemRowHolder.ivradio.setVisibility(View.VISIBLE);
            }

            loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

            itemRowHolder.tvName.setText(addr.getDeliverTo());
            String adres=addr.getAddress1()+addr.getAddress2()+"\nPincode-"+addr.getPinCode();
            itemRowHolder.tvAddress.setText(adres);
            itemRowHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String addrid = addr.getId();

                    if (Utils.isNetworkConnectedMainThred(AddressListActivity.this)) {
                        deleteAddress(addrid);

                    } else {
                        Toast.makeText(AddressListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                    }
                }
            });


            itemRowHolder.llAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_index=i;
                    notifyDataSetChanged();
                    areaId=addr.getId();
                }
            });




        }
        @Override
        public int getItemCount() {
            return addrmodel.size();
        }
        public class Holder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tvAddress;
            TextView tvPhone;

            ImageView ivradio;
            ImageView ivradioOn;
            ImageView ivDelete;

            LinearLayout llAddress;


            public Holder(View view) {
                super(view);
                tvName=itemView.findViewById(R.id.tvName);
                tvAddress=itemView.findViewById(R.id.tvAddress);
                tvPhone=itemView.findViewById(R.id.tvPhone);
                ivradio=itemView.findViewById(R.id.ivradio);
                ivradioOn=itemView.findViewById(R.id.ivradioOn);
                llAddress=itemView.findViewById(R.id.llAddress);
                ivDelete=itemView.findViewById(R.id.ivDelete);
            }
        }
    }


    public void getAddress(){
        //loader.setMessage("Loading...Please Wait..");
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GETADDRESS,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("testttttttt", response);
                loader.dismiss();
                addresslist.clear();
                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("Success").equalsIgnoreCase("true")) {
                        JSONArray array = jsonObject.getJSONArray("Address");
                        {
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject addr = array.getJSONObject(i);
                                AddressItem additem = new AddressItem();
                                additem.setId(addr.getString("id"));
                                additem.setCustomerId(addr.getString("customer_id"));
                                additem.setDeliverTo(addr.getString("deliver_to"));
                                additem.setStateId(addr.getString("state_id"));
                                additem.setCityId(addr.getString("city_id"));
                                additem.setMobileNo(addr.getString("mobile_no"));
                                additem.setAddress1(addr.getString("address1"));
                                additem.setAddress2(addr.getString("address2"));
                                additem.setPinCode(addr.getString("pin_code"));
                              // additem.setState_name(addr.getString("state_name"));
                              //  additem.setCity_name(addr.getString("city_name"));
                                   addresslist.add(additem);

                            }
                        }
                    }
                    else {
                        Toast.makeText(AddressListActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }
                    //creating adapter object and setting it to recyclerview
                    addressAdapter = new AddressAdapter(AddressListActivity.this, addresslist);
                    binding.recyclerview.setAdapter(addressAdapter);
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
                params.put("customer_id", pref.get(Constants.USERID));
                Log.e("",""+params);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void deleteAddress(final String addrid) {
        //loader.setMessage("Loading...Please Wait..");
      //  loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.DELETEADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // loader.dismiss();

                try {
                    //converting the string to json array object
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equalsIgnoreCase("true")) {

                        String mgs = jsonObject.getString("message");
                        Toast.makeText(AddressListActivity.this, mgs, Toast.LENGTH_LONG).show();
                        if (Utils.isNetworkConnectedMainThred(AddressListActivity.this)) {
                            getAddress();

                        } else {
                            Toast.makeText(AddressListActivity.this, "No Internet Connection!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        // layout_cart_empty.setVisibility(View.VISIBLE);
                        Toast.makeText(AddressListActivity.this,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
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
                params.put("id", addrid);

                Log.e("deleteee", "" + params);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(AddressListActivity.this).add(stringRequest);
    }

}