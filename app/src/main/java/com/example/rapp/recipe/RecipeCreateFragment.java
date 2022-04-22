package com.example.rapp.recipe;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.iNetworkCallback;

import org.json.JSONException;

import java.util.ArrayList;

public class RecipeCreateFragment extends Fragment {

    private static final String TAG = "CreateRecipeFragment";
    private View view;
    private MainActivity mMainActivity;

    public RecipeCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe_create, container, false);
        mMainActivity = (MainActivity) requireActivity();
        ArrayList<EditText> editTexts = new ArrayList<EditText>();

        EditText getTitle = (EditText) view.findViewById(R.id.createRecipeTitle);
        EditText getDesc = (EditText) view.findViewById(R.id.createRecipeDescription);
        LinearLayout ingredientLayout = (LinearLayout) view.findViewById(R.id.ingredientButtons);


        Button addIngredientButton = (Button) view.findViewById(R.id.addIngredient_button);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = mMainActivity.getApplicationContext();
                EditText lEditText = new EditText(context);
                lEditText.setText("Ingredient", TextView.BufferType.EDITABLE);
                int ID = View.generateViewId();
                lEditText.setId(ID);
                editTexts.add(lEditText);
                ingredientLayout.addView(lEditText);
            }
        });

        Button createRecipeButton = (Button) view.findViewById(R.id.save_button);
        createRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getTitle.getText().toString();
                String description = getDesc.getText().toString();
                ArrayList<String> ingredients = new ArrayList<>();

                for(int i = 0; i < editTexts.size(); i++) {
                    EditText test = editTexts.get(i);
                    ingredients.add(test.getText().toString());
                }

                Recipe recipe = new Recipe(title, description, ingredients, true);
                int pageid = (int) getArguments().getLong("pageID");
                try {
                    mMainActivity.getNetworkManager().createRecipe(recipe, pageid, new iNetworkCallback<Recipe>() {
                        @Override
                        public void onSuccess(Recipe result) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("pageId", pageid);
                            Navigation.findNavController(view).navigate(R.id.pageMainFragment, bundle);
                        }

                        @Override
                        public void onFailure(String errorString) {
                            Log.e(TAG, "Failed to create Recipe: " + errorString);
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