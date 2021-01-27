package com.absolutecurepharma.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.R;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.SubCategoryAdapter;

public class ProductListFragment extends Fragment {

    View view;
    RecyclerView rvProduct;
    ProductAdapter productAdapter;
    int [] productimage={R.drawable.product_image1,R.drawable.product_image2,R.drawable.product_image3,R.drawable.product_image4};
    String[] productname={"Paracetamol","Borncorid","Cetaphil","Head&shoulders"};
    String[] productsize={"500mg","200ml","118ml","400ml",};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.product_list_fragment, container, false);
        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        rvProduct=view.findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(),2));
        productAdapter=new ProductAdapter(getActivity(),productimage,productname,productsize);
        rvProduct.setAdapter(productAdapter);


        return view;
    }

}
