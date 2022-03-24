package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rapp.entities.Recipe;

public class RecipeChangeActivity extends AppCompatActivity {

    private Button mFrontPageButton, mRecipeMainButton, mSaveButton;
    private EditText mEditRecipeTitle, mEditRecipeDescription;
    private int mRecipeId;
    private Recipe mRecipe;
    private RecipeDbMock mRecipeBank = new RecipeDbMock();
    private static final String TAG = "RecipeChangeActivity";
    private static final String EXTRA_RECIPE_ID = "com.example.rapp.recipeId";

    public static Intent newIntent(Context packageContext, int recipeId) {
        Intent intent = new Intent(packageContext, RecipeChangeActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_change);

        mRecipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 0);
        mRecipe = mRecipeBank.getRecipeById(mRecipeId);

        mEditRecipeTitle = (EditText) findViewById(R.id.edit_recipe_title);
        mEditRecipeTitle.setText(mRecipe.getTitle());
        mEditRecipeDescription = (EditText) findViewById(R.id.edit_recipe_description);
        mEditRecipeDescription.setText(mRecipe.getDescription());

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

        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mEditRecipeTitle.getText().toString();
                String description = mEditRecipeDescription.getText().toString();
                mRecipeBank.setRecipeTitle(mRecipeId, title);
                mRecipeBank.setRecipeDescription(mRecipeId, description);
            }
        });
    }
}