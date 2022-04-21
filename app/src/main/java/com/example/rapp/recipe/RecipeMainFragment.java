package com.example.rapp.recipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
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


public class RecipeMainFragment extends Fragment {

    private View view;
    private static final String TAG = "RecipeMainFragment";
    private Recipe mRecipe;
    private MainActivity act;

    private final View.OnClickListener changeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putLong("recipeId", mRecipe.getID());
            act.getNavController().navigate(R.id.action_recipeMainFragment_to_recipeChangeFragment, bundle);
        }
    };

    public RecipeMainFragment() {
        // Required empty public constructor
    }

    private void setupRecipe() {
        TextView title = view.findViewById(R.id.recipe_title_textView);
        TextView description = view.findViewById(R.id.recipe_description_textView);
        TextView ingredients = view.findViewById(R.id.recipe_ingredients_textView);
        act.getNetworkManager().getRecipeById(getArguments().getLong("recipeId"),
                new iNetworkCallback<Recipe>() {
                    @Override
                    public void onSuccess(Recipe result) {
                        mRecipe = result;
                        title.setText(mRecipe.getTitle());
                        description.setText(mRecipe.getDescription());
                        String s = "";
                        for(String ingredient : mRecipe.getIngredients()) s += ingredient;
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
        view = inflater.inflate(R.layout.fragment_recipe_main, container, false);
        act = (MainActivity) requireActivity();
        setupRecipe();
        Button change = (Button) view.findViewById(R.id.recipe_change_button);
        change.setOnClickListener(changeListener);
        return view;
    }
}