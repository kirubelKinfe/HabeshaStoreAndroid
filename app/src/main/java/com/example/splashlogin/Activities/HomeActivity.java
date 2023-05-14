package com.example.splashlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.splashlogin.Fragments.CartFragment;
import com.example.splashlogin.Fragments.FavoritesFragment;
import com.example.splashlogin.Fragments.HomeFragment;
import com.example.splashlogin.Fragments.ProfileFragment;
import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    public static int cartNumber = 0;
    BottomNavigationView bottomNavigationView;

    public HomeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.getOrCreateBadge(R.id.item2).setNumber(cartNumber);
        bottomNavigationView.getOrCreateBadge(R.id.item2).setVisible(true);
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.item1:
                selectedFragment = new HomeFragment();
                break;
            case R.id.item2:
                selectedFragment = new CartFragment();
                break;
            case R.id.item3:
                selectedFragment = new FavoritesFragment();
                break;
            case R.id.item4:
                selectedFragment = new ProfileFragment();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_layout,
                selectedFragment
        ).commit();

        return true;
    };

    public void onLogoutClick(View view) {
        finishAndRemoveTask();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);

    }
}