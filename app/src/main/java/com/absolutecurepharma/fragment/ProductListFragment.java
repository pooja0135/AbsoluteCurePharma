package com.absolutecurepharma.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.ProductDetailActivity;
import com.absolutecurepharma.R;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.ProductListAdapter;

public class ProductListFragment extends Fragment {

    View view;
    RecyclerView rvProduct;
    ProductListAdapter productAdapter;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &Supplements"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.product_list_fragment, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                       replaceFragmentWithAnimation(new DashboardFragment());
                        return true;
                    }
                }
                return false;
            }
        });

        rvProduct=view.findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        productAdapter= new ProductListAdapter(getActivity(), categoryimage, categoryname) {
            @Override
            protected void onSubCategoryClick(View view, String str) {
                startActivity(new Intent(getActivity(), ProductDetailActivity.class));
            }
        };
        rvProduct.setAdapter(productAdapter);


        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

}
