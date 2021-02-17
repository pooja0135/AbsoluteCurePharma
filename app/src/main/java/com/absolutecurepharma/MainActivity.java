package com.absolutecurepharma;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SeverCall.Constants;
import com.absolutecurepharma.fragment.DashboardFragment;
import com.absolutecurepharma.utils.Preferences;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static NavigationView navigationView;
    DrawerLayout drawer;
    RelativeLayout rlHome;
    LinearLayout llOrder;
    LinearLayout llAccount;
    LinearLayout llCart;
    LinearLayout llSearch;
    LinearLayout llShare;
    LinearLayout llLogout;
    public static ImageView ivMenu,ivCart;
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref=new Preferences(this);
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
        llShare = headerview.findViewById(R.id.llShare);
        llLogout = headerview.findViewById(R.id.llLogout);



        //setOnClickListener
        rlHome.setOnClickListener(this);
        llAccount.setOnClickListener(this);
        llCart.setOnClickListener(this);
        llOrder.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llLogout.setOnClickListener(this);
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
                startActivity(new Intent(this,MyAccountActivity.class));
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
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llSearch:
                startActivity(new Intent(this,SearchActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llLogout:
                logout();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llShare:
                sendReferral();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.llOrder:
                startActivity(new Intent(this,OrderListActivity.class));
                break;
        }
    }


    //===========================Dialog===============================//
    public void logout() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertyesno);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);


        TextView tvYes = (TextView) dialog.findViewById(R.id.tvOk);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tvcancel);
        TextView tvReason = (TextView) dialog.findViewById(R.id.textView22);
        TextView tvAlertMsg = (TextView) dialog.findViewById(R.id.tvAlertMsg);

        tvAlertMsg.setText("Confirmation Alert..!!!");
        tvReason.setText("Are you sure want to logout?");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        tvYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                pref.set(Constants.USERID, "");
                pref.commit();
                Toast.makeText(MainActivity.this, "Logged out..!!!", Toast.LENGTH_SHORT).show();
                finishAffinity();
                dialog.dismiss();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void sendReferral() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.absolutecurepharma"+" .I'm inviting you to used AbsoluteCurePharma,an app for purchasing product.");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "I'm inviting you to used AbsoluteCurePharma ,an app for purchasing product.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"Share link!"));



    }

}