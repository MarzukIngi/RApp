package com.example.rapp.recipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.util.List;


public class RecipeMainFragment extends Fragment {

    private View view;
    private static final String TAG = "RecipeMainFragment";
    private Recipe mRecipe;

    public RecipeMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_main, container, false);
        TextView title = view.findViewById(R.id.recipe_title_textView);
        TextView description = view.findViewById(R.id.recipe_decsription_textView);
        TextView ingredients = view.findViewById(R.id.recipe_ingredients_textView);
        MainActivity act = (MainActivity) requireActivity();

        act.getNetworkManager().getRecipeById(getArguments().getLong("recipeId"),
                new iNetworkCallback<Recipe>() {
            @Override
            public void onSuccess(Recipe result) {
                mRecipe = result;
                title.setText(mRecipe.getTitle());
                description.setText(mRecipe.getDescription());
                /*String s = "";
                for(String ingredient : mRecipe.getIngredients()) s += ingredient + " ";
                ingredients.setText(s);*/
            }
            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Recipes: " + errorString);
            }
        });
        return view;
    }
}