package com.example.testingapps.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.testingapps.R;
import com.example.testingapps.view.fragment.AboutFragment;
import com.example.testingapps.view.fragment.HomeFragment;
import com.example.testingapps.view.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class MenuUtamaActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.inflateMenu(R.menu.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home_nav);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.body_layout, new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_nav:
                        fragment = new HomeFragment();
                        break;
                    case R.id.profile_nav:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.about_nav:
                        fragment = new AboutFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.body_layout, fragment).commit();
                return getFragmentPage(fragment);
            }
        });
    }

    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.body_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}


