package com.absolutecurepharma.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        rvProduct=view.findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        productAdapter= new ProductListAdapter(getActivity(), categoryimage, categoryname) {
            @Override
            protected void onSubCategoryClick(View view, String str) {
                replaceFragmentWithAnimation(new ProductListFragment());
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
