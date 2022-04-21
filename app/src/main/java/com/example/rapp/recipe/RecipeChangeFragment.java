package com.example.rapp.recipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.iNetworkCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RecipeChangeFragment extends Fragment {

    private View view;
    private static final String TAG = "RecipeChangeFragment";
    private Recipe mRecipe = new Recipe();
    private MainActivity mMainActivity;
    private TextView title, description, ingredients;

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView title = view.findViewById(R.id.recipe_title_change_textview);
            TextView description = view.findViewById(R.id.recipe_description_change_textView);
            //TextView ingredients = view.findViewById(R.id.recipe_ingredients_change_textView);
            mRecipe.setTitle(title.getText().toString());
            mRecipe.setDescription(description.getText().toString());
            Log.d(TAG, mRecipe.getTitle());
        }
    };

    public RecipeChangeFragment() {
        // Required empty public constructor
    }

    private void setupRecipe() {
        TextView title = view.findViewById(R.id.recipe_title_change_textview);
        TextView description = view.findViewById(R.id.recipe_description_change_textView);
        TextView ingredients = view.findViewById(R.id.recipe_ingredients_change_textView);
        mMainActivity.getNetworkManager().getRecipeById(getArguments().getLong("recipeId"),
                new iNetworkCallback<Recipe>() {
                    @Override
                    public void onSuccess(Recipe result) {
                        mRecipe = result;
                        title.setText(mRecipe.getTitle());
                        description.setText(mRecipe.getDescription());
                        String s = "";
                    for(String ingredient : mRecipe.getIngredients()) s += ingredient + " ";
                    ingredients.setText(s);
                    }
                    @Override
                    public void onFailure(String errorString) {
                        Log.e(TAG, "Failed to get Recipes: " + errorString);
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_change, container, false);
        mMainActivity = (MainActivity) requireActivity();
        TextView title = view.findViewById(R.id.recipe_title_change_textview);
        TextView description = view.findViewById(R.id.recipe_description_change_textView);
        TextView ingredients = view.findViewById(R.id.recipe_ingredients_change_textView);
        setupRecipe();
        Button save = (Button) view.findViewById(R.id.recipe_change_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecipe.setID(getArguments().getLong("recipeId"));
                mRecipe.setTitle(title.getText().toString());
                mRecipe.setDescription(description.getText().toString());
                mRecipe.setPage(null);
                List<String> ingr = new ArrayList<>();
                String[] s = ingredients.getText().toString().split(" ");
                for(String str: s) ingr.add(str);
                mRecipe.setIngredients(ingr);
                mRecipe.setPublished(true);
                try {
                    mMainActivity.getNetworkManager().changeRecipeById(getArguments()
                                    .getLong("recipeId"), mRecipe,
                            new iNetworkCallback<Recipe>() {
                                @Override
                                public void onSuccess(Recipe result) {
                                    Log.d(TAG, "Title is " + result.getTitle());
                                }

                                @Override
                                public void onFailure(String errorString) {
                                    Log.d(TAG, errorString);
                                }
                            });
                } catch(JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            });
        return view;
    }
}

