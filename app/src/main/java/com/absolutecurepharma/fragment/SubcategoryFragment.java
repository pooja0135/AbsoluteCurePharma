package com.absolutecurepharma.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.absolutecurepharma.R;
import com.absolutecurepharma.adapter.CardPagerAdapter;
import com.absolutecurepharma.adapter.CategoryAdapter;
import com.absolutecurepharma.adapter.ProductAdapter;
import com.absolutecurepharma.adapter.SubCategoryAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class SubcategoryFragment extends Fragment {

    View view;
    RecyclerView rvSubCategory;
    SubCategoryAdapter subCategoryAdapter;
    int [] categoryimage={R.drawable.pharmacy,R.drawable.cosmetics,R.drawable.ayurvedic,R.drawable.vitamin_supplements};
    String[] categoryname={"Pharmacy","Cosmetics","Ayurvedic","Vitamin &Supplements"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.subcategory_fragment, container, false);
        // DrawerActivity.ivHome.setVisibility(View.VISIBLE);

        rvSubCategory=view.findViewById(R.id.rvSubCategory);
        rvSubCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        subCategoryAdapter= new SubCategoryAdapter(getActivity(), categoryimage, categoryname) {
            @Override
            protected void onSubCategoryClick(View view, String str) {
                replaceFragmentWithAnimation(new ProductListFragment());
            }
        };
        rvSubCategory.setAdapter(subCategoryAdapter);


        return view;
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

}
