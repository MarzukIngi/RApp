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

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.entities.Recipe;
import com.example.rapp.networking.iNetworkCallback;
import java.util.List;
import java.util.Random;

/**
 * Sér um forsíðu fragment-ið.
 */
public class FrontPageFragment extends Fragment {

    private LinearLayout mLinearLayout;
    private List<Recipe> mRecipes;
    private int mIterator;
    private static final String TAG = "FrontPageFragment";
    private View view;
    private MainActivity mMainActivity;
    private Button mRandomRecipeButton;
    private Button mRandomPageButton;

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

    /**
     * Setur upp takkana á forsíðunni sem vísa í vinsælustu uppskriftirnar.
     */
    public void setupRecipeButtons() {
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
                            bundle.putString("back", "front");
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
        LinearLayout pageLayout = view.findViewById(R.id.pages_linear_layout);
        mMainActivity = (MainActivity) requireActivity();
        Context context = mMainActivity.getApplicationContext();
        setupRecipeButtons();
        mMainActivity.getNetworkManager().getPages(4, new iNetworkCallback<List<Page>>() {
            @Override
            public void onSuccess(List<Page> result) {
                for(int i = 0; i < result.size(); i++) {
                    Page page = result.get(i);
                    Button b = (Button) pageLayout.getChildAt(i+2);
                    b.setText(page.getTitle());
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("pageId", page.getID());
                            mMainActivity.getNavController().navigate(R.id.pageMainFragment, bundle);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, "Failed to get Pages: " + errorString);
            }
        });

        mRandomRecipeButton = (Button) view.findViewById(R.id.random_recipe_button);
        mRandomRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int r = random.nextInt(4);
                Button b = (Button) mLinearLayout.getChildAt(r + 2);
                b.performClick();
            }
        });

        LinearLayout l = (LinearLayout) view.findViewById(R.id.pages_linear_layout);
        mRandomPageButton = (Button) view.findViewById(R.id.random_page_button);
        mRandomPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int r = random.nextInt(4);
                Button b = (Button) l.getChildAt(r + 2);
                b.performClick();
            }
        });

        return view;
    }
}