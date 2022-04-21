package com.example.rapp.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.iNetworkCallback;
import java.util.List;
import java.util.Random;


public class FrontPageFragment extends Fragment {

    private LinearLayout mLinearLayout;
    private List<Recipe> mRecipes;
    private int mIterator;
    private static final String TAG = "FrontPageFragment";
    private View view;
    private MainActivity mMainActivity;
    private Button mRandomButton;

    private final iNetworkCallback<List<Recipe>> iNC = new iNetworkCallback<List<Recipe>>() {
        @Override
        public void onSuccess(List<Recipe> result) {
            mRecipes = result;
        }
        @Override
        public void onFailure(String errorString) {
            Log.e(TAG, "Failed to get Recipes: " + errorString);
        }
    };

    public FrontPageFragment() {
        // Required empty public constructor
    }

    private void setupRecipeButtons() {
        mMainActivity.getNetworkManager().getTrendingRecipes(new iNetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                mRecipes = result;
                mIterator = Math.min(mRecipes.size(), 4);
                for(int i = 0; i < mIterator; i++) {
                    Recipe r = mRecipes.get(i);
                    Button b = (Button) mLinearLayout.getChildAt(i+2);
                    b.setText(r.getTitle());
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("recipeId", r.getID());
                            mMainActivity.getNavController().navigate(
                                    R.id.action_frontPageFragment_to_recipeMainFragment,
                                    bundle);
                        }
                    });
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
        view = inflater.inflate(R.layout.fragment_front_page, container, false);
        mLinearLayout = view.findViewById(R.id.recipes_linear_layout);
        mMainActivity = (MainActivity) requireActivity();
        setupRecipeButtons();
        mRandomButton = (Button) view.findViewById(R.id.random_recipe_button);
        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int r = random.nextInt(4);
                Button b = (Button) mLinearLayout.getChildAt(r + 2);
                b.performClick();
            }
        });
        return view;
    }
}