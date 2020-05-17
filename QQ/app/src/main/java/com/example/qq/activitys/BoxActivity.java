package com.example.qq.activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.qq.R;
import com.example.qq.fragments.home.HomeFragment;
import com.example.qq.fragments.own.OwnFragment;
import com.example.qq.fragments.trends.TrendFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoxActivity extends AppCompatActivity {

    @BindView(R.id.fragment_box)
    FrameLayout fragmentBox;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    HomeFragment homeFragment;
    TrendFragment trendFragment;
    OwnFragment ownFragment;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        ButterKnife.bind(this);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.fragment_home:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_box,homeFragment).commit();
                        break;
                    case R.id.fragment_trend:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_box,trendFragment).commit();
                        break;
                    case R.id.fragment_own:
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_box,ownFragment).commit();
                        break;
                }
                return false;
            }
        });

        initView();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_box,homeFragment).commit();
    }

    private void initView() {
        homeFragment = new HomeFragment();
        trendFragment = new TrendFragment();
        ownFragment = new OwnFragment();
    }
}
