package com.example.rapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.NetworkManager;
import com.example.rapp.networking.iNetworkCallback;
import com.example.rapp.page.FrontPageFragment;
import com.example.rapp.recipe.RecipeMainFragment;
import com.example.rapp.user.UserMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Recipe> mTest;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        //Prófa að sækja gögn frá bakenda
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getRecipes(new iNetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mTest = result;
                Log.d(TAG, "First recipe in list: " + mTest.get(0).getTitle());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Recipes: " + errorString);
            }
        });

    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    switch (item.getItemId()) {
                        case R.id.nav_front:
                            selected = new FrontPageFragment();
                            break;
                        case R.id.nav_recipes:
                            selected = new RecipeMainFragment();
                            break;
                        case R.id.nav_pages:
                            selected = new RecipeMainFragment();
                            break;
                        case R.id.nav_profile:
                            selected = new UserMainFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                            selected).commit();
                    return true; // return true;
                }
            };


}