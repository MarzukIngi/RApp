package com.example.rapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.NetworkManager;
import com.example.rapp.networking.iNetworkCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

/**
 * Sér um main activity, sem heldur utan um fragment-in og bottom navigation.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NetworkManager mNetworkManager;
    private List<Recipe> mRecipeResults;
    private NavController mNavController;

    public NavController getNavController() {
        return mNavController;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment =(NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        mNavController = navHostFragment.getNavController();
        mNetworkManager = NetworkManager.getInstance(this);
        mNetworkManager.getRecipes(new iNetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mRecipeResults = result;
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Recipes: " + errorString);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    switch (item.getItemId()) {
                        case R.id.nav_front:
                            //selected = new FrontPageFragment();
                            mNavController.navigate(R.id.frontPageFragment);
                            break;
                        case R.id.nav_pages:
                            //selected = new RecipeMainFragment();
                            mNavController.navigate(R.id.pageCreateFragment);
                            break;
                        case R.id.nav_profile:
                            //selected = new UserMainFragment();
                            mNavController.navigate(R.id.userMainFragment);
                            break;
                    }
                    return true; // return true;
                }
            };


}