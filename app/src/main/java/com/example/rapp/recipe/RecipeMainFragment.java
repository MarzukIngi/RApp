package com.example.rapp.recipe;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.iNetworkCallback;

/**
 * Sér um fragment-ið sem birtir uppskrift.
 */
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

    /**
     * Sækir uppskriftina og setur hana upp í fragment-inu.
     */
    private void setupRecipe() {
        TextView title = view.findViewById(R.id.recipe_title_textView);
        TextView description = view.findViewById(R.id.recipe_description_textView);
        LinearLayout ingredients = view.findViewById(R.id.recipeIngredients);
        Context context = act.getApplicationContext();
        act.getNetworkManager().getRecipeById(getArguments().getLong("recipeId"),
                new iNetworkCallback<Recipe>() {
                    @Override
                    public void onSuccess(Recipe result) {
                        mRecipe = result;
                        title.setText(mRecipe.getTitle());
                        description.setText(mRecipe.getDescription());
                        String s = "";
                        for(String ingredient : mRecipe.getIngredients()) {
                            TextView ingredientView = new TextView(context);
                            ingredientView.setId(View.generateViewId());
                            ingredientView.setText(ingredient);
                            ingredients.addView(ingredientView);
                        }
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
        Button back = (Button) view.findViewById(R.id.recipe_back_button);
        change.setOnClickListener(changeListener);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = getArguments().getString("back");
                Long pog = getArguments().getLong("recipeId");
                if(getArguments().getString("back").equals("page")) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("pageId", getArguments().getLong("pageId"));
                    act.getNavController().navigate(R.id.pageMainFragment, bundle);
                } else {
                    act.getNavController().navigate(R.id.frontPageFragment);
                }
            }
        });

        return view;
    }
}