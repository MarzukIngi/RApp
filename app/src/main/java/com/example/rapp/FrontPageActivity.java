package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class FrontPageActivity extends AppCompatActivity {

    private Button mUserMainButton, mPageMainButton, mPageCreateButton, mRandomButton;
    private Button mRecipe1Button, mRecipe2Button, mRecipe3Button, mRecipe4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mUserMainButton = (Button) findViewById(R.id.user_main_button);
        mUserMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });

        mPageMainButton = (Button) findViewById(R.id.page_main_button);
        mPageMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, PageMainActivity.class);
                startActivity(intent);
            }
        });

        mPageCreateButton = (Button) findViewById(R.id.page_create_button);
        mPageCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, PageCreateActivity.class);
                startActivity(intent);
            }
        });

        mRecipe1Button = (Button) findViewById(R.id.recipe_1_button);
        mRecipe1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RecipeMainActivity.newIntent(FrontPageActivity.this, 0);
                startActivity(intent);
            }
        });

        mRecipe2Button = (Button) findViewById(R.id.recipe_2_button);
        mRecipe2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RecipeMainActivity.newIntent(FrontPageActivity.this, 1);
                startActivity(intent);
            }
        });

        mRecipe3Button = (Button) findViewById(R.id.recipe_3_button);
        mRecipe3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RecipeMainActivity.newIntent(FrontPageActivity.this, 2);
                startActivity(intent);
            }
        });

        mRecipe4Button = (Button) findViewById(R.id.recipe_4_button);
        mRecipe4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RecipeMainActivity.newIntent(FrontPageActivity.this, 3);
                startActivity(intent);
            }
        });

        mRandomButton = (Button) findViewById(R.id.random_recipe_button);
        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int recipeId = random.nextInt(4);
                Intent intent = RecipeMainActivity.newIntent(FrontPageActivity.this, recipeId);
                startActivity(intent);
            }
        });
    }
}