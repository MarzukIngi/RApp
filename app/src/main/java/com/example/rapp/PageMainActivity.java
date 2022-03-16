package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PageMainActivity extends AppCompatActivity {

    private Button mFrontPageButton;
    private Button mRecipeMainButton;
    private Button mPageChangeButton;
    private Button mRecipeCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_main);

        mFrontPageButton = (Button) findViewById(R.id.front_page_button);
        mFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageMainActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });

        mRecipeMainButton = (Button) findViewById(R.id.recipe_main_button);
        mRecipeMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageMainActivity.this, RecipeMainActivity.class);
                startActivity(intent);
            }
        });

        mPageChangeButton = (Button) findViewById(R.id.page_change_button);
        mPageChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageMainActivity.this, PageMainActivity.class);
                startActivity(intent);
            }
        });

        mRecipeCreateButton = (Button) findViewById(R.id.recipe_create_button);
        mRecipeCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageMainActivity.this, RecipeCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}