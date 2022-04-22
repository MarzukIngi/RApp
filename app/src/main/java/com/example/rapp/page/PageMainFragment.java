package com.example.rapp.page;

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
import com.example.rapp.entities.Page;
import com.example.rapp.entities.Recipe;
import com.example.rapp.entities.User;
import com.example.rapp.networking.iNetworkCallback;

import java.util.ArrayList;
import java.util.List;

public class PageMainFragment extends Fragment {

    private View view;
    private static final String TAG = "PageMainFragment";
    private Page mPage;
    private MainActivity mMainActivity;

    public PageMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_main, container, false);
        mMainActivity = (MainActivity) requireActivity();
        Context context = mMainActivity.getApplicationContext();
        ArrayList<Button> recipeButtons = new ArrayList<>();

        TextView title = view.findViewById(R.id.page_title);
        TextView description = view.findViewById(R.id.page_description);
        TextView owner = view.findViewById(R.id.owner_name);
        LinearLayout recipesLayout = view.findViewById(R.id.pageRecipes);
        long id = getArguments().getLong("pageId");

        mMainActivity.getNetworkManager().getPage(id, new iNetworkCallback<Page>() {
            @Override
            public void onSuccess(Page result) {
                mPage = result;
                title.setText(result.getTitle());
                description.setText(result.getDescription());

                ArrayList<Recipe> recipes = (ArrayList<Recipe>) mPage.getRecipes();

                for(int i = 0; i < recipes.size(); i++) {
                    Recipe recipe = recipes.get(i);
                    Button recipeButton = new Button(context);
                    recipeButton.setId(View.generateViewId());
                    recipeButton.setText(recipe.getTitle());
                    //recipeButtons.add(recipeButton);
                    recipeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("pageId", result.getID());
                            bundle.putLong("recipeId", recipe.getID());
                            bundle.putString("back", "page");
                            mMainActivity.getNavController().navigate(R.id.recipeMainFragment, bundle);
                        }
                    });
                    recipesLayout.addView(recipeButton);
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Page: " + errorString);
            }

        });

        mMainActivity.getNetworkManager().getOwner(id, new iNetworkCallback<User>() {
            @Override
            public void onSuccess(User result) {
                mPage.setUser(result);
                Log.d(TAG, result.getUserName());
                owner.setText(result.getUserName());
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Owner: " + errorString);
            }
        });

        Button createRecipeButton = (Button) view.findViewById(R.id.createRecipe_button);
        createRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("pageID", mPage.getID());
                mMainActivity.getNavController().navigate(R.id.recipeCreateFragment, bundle);
            }
        });


        return view;
    }
}