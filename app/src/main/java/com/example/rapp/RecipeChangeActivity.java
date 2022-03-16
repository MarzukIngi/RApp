package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeChangeActivity extends AppCompatActivity {

    private Button mFrontPageButton;
    private Button mRecipeMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_change);

        mFrontPageButton = (Button) findViewById(R.id.front_page_button);
        mFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeChangeActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });

        mRecipeMainButton = (Button) findViewById(R.id.recipe_main_button);
        mRecipeMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeChangeActivity.this, RecipeMainActivity.class);
                startActivity(intent);
            }
        });
    }
}