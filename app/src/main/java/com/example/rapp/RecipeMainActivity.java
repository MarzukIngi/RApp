package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rapp.entities.Recipe;

public class RecipeMainActivity extends AppCompatActivity {

    private Button mFrontPageButton, mRecipeChangeButton, mUserMainButton;
    private TextView mTitleTextView, mDescriptionTextView;
    private int mRecipeId;
    private static final String TAG = "RecipeMainActivity";
    private static final String EXTRA_RECIPE_ID = "com.example.rapp.recipeId";
    // Dummy gögn
    private Recipe[] mRecipeBank = new Recipe[]{
            new Recipe("Kjúklinga Tikka Masala", "Þetta er kjúklingur"),
            new Recipe("Cheerios", "Settu morgunkornið fyrst og síðan mjólkina"),
            new Recipe("Spagettí", "Nennir einhver að nota skeið?"),
            new Recipe("Ristað brauð", "Maður segir brauðrist en ekki ristavél")
    };

    public static Intent newIntent(Context packageContext, int recipeId) {
        Intent intent = new Intent(packageContext, RecipeMainActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        mRecipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 0);
        mTitleTextView = (TextView) findViewById(R.id.title_text_view);
        mTitleTextView.setText(mRecipeBank[mRecipeId].getTitle());
        mDescriptionTextView = (TextView) findViewById(R.id.description_text_view);
        mDescriptionTextView.setText(mRecipeBank[mRecipeId].getDescription());

        mFrontPageButton = (Button) findViewById(R.id.front_page_button);
        mFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMainActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });

        mRecipeChangeButton = (Button) findViewById(R.id.recipe_change_button);
        mRecipeChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMainActivity.this, RecipeChangeActivity.class);
                startActivity(intent);
            }
        });

        mUserMainButton = (Button) findViewById(R.id.user_main_button);
        mUserMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeMainActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });
    }
}