package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.NetworkManager;
import com.example.rapp.networking.iNetworkCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mRecipesButton;
    private Button mLogInButton;
    private List<Recipe> mTest;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mRecipesButton = (Button) findViewById(R.id.front_page_button);
        mRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });
        mLogInButton = (Button) findViewById(R.id.login_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, LogInActivity.class);
               startActivity(intent);
            }
        });
    }
}