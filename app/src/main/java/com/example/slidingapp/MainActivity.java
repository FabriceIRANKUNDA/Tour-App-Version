package com.example.slidingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListerner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(this)).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListerner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFrag = new HomeFragment(MainActivity.this);
                            break;
                        case R.id.nav_search:
                            selectedFrag = new SearchFragment();
                            break;
                        case R.id.nav_fav:
                            selectedFrag = new FavoritesFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFrag = new AddNewTourFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();
                    return true;
                }
            };
}
