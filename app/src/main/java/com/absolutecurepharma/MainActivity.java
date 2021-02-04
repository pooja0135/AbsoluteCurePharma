package com.absolutecurepharma;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.absolutecurepharma.fragment.DashboardFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static NavigationView navigationView;
    DrawerLayout drawer;
    RelativeLayout rlHome;
    LinearLayout llOrder;
    LinearLayout llAccount;
    LinearLayout llCart;
    LinearLayout llSearch;
    public static ImageView ivMenu,ivCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivMenu = findViewById(R.id.ivMenu);
        ivCart = findViewById(R.id.ivCart);
        llSearch = findViewById(R.id.llSearch);
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        rlHome = headerview.findViewById(R.id.rlHome);
        llOrder = headerview.findViewById(R.id.llOrder);
        llAccount = headerview.findViewById(R.id.llAccount);
        llCart = headerview.findViewById(R.id.llCart);



        //setOnClickListener
        rlHome.setOnClickListener(this);
        llAccount.setOnClickListener(this);
        llCart.setOnClickListener(this);
        llOrder.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        ivMenu.setOnClickListener(this);


        replaceFragmentWithAnimation(new DashboardFragment());
    }

    public void replaceFragmentWithAnimation(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //  transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rlHome:
                replaceFragmentWithAnimation(new DashboardFragment());
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.llAccount:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.llCart:
                startActivity(new Intent(this,CartListActivity.class));
                break;

            case R.id.ivMenu:
                drawer.openDrawer(Gravity.LEFT);
               /* if (ivMenu.getTag().equals("arrow")) {
                }
                else
                    {
                    drawer.openDrawer(Gravity.LEFT);
                }*/
                break;
            case R.id.ivCart:
                startActivity(new Intent(this,CartListActivity.class));
                break;
            case R.id.llSearch:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            case R.id.llOrder:
                startActivity(new Intent(this,OrderListActivity.class));
                break;
        }
    }
}