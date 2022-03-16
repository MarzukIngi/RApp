package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeCreateActivity extends AppCompatActivity {

    private Button mFrontPageButton;
    private Button mRecipeMainButton;
    private Button mPageMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);

        mFrontPageButton = (Button) findViewById(R.id.front_page_button);
        mFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeCreateActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });

        mRecipeMainButton = (Button) findViewById(R.id.recipe_main_button);
        mRecipeMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeCreateActivity.this, RecipeMainActivity.class);
                startActivity(intent);
            }
        });

        mPageMainButton = (Button) findViewById(R.id.page_main_button);
        mPageMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeCreateActivity.this, PageMainActivity.class);
                startActivity(intent);
            }
        });
    }
}