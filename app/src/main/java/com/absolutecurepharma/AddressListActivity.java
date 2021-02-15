package com.absolutecurepharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.absolutecurepharma.databinding.ActivityAddressListBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class AddressListActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddressListBinding binding;
    ArrayList<HashMap<String, String>> addresslist = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address_list);

        binding.recyclerview.setLayoutManager(new GridLayoutManager(this,1));
        binding.recyclerview.setAdapter(new AddressAdapter(addresslist));

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



    private class FavNameHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvAddress;
        TextView tvPhone;

        ImageView ivradio;
        ImageView ivradioOn;

        LinearLayout llAddress;

        public FavNameHolder(View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvPhone=itemView.findViewById(R.id.tvPhone);
            ivradio=itemView.findViewById(R.id.ivradio);
            ivradioOn=itemView.findViewById(R.id.ivradioOn);
            llAddress=itemView.findViewById(R.id.llAddress);
        }
    }
    private class AddressAdapter extends RecyclerView.Adapter<FavNameHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public AddressAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }
        public AddressAdapter() {

        }
        public FavNameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FavNameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_items, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull final FavNameHolder holder, final int position) {




        }

        public int getItemCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }
}