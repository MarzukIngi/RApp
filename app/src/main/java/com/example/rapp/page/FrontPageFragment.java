package com.example.rapp.page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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


public class FrontPageFragment extends Fragment {

    private LinearLayout mLinearLayout;
    private List<Recipe> mRecipes;
    private int mIterator;
    private static final String TAG = "FrontPageFragment";
    private View view;
    private MainActivity mMainActivity;

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

    private void addListeners() {
        for(int i = 0; i < 4; i++) {
            Button b = (Button) mLinearLayout.getChildAt(i+2);
           // Recipe r = mRecipes.get(i);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("recipeId", 2);
                    mMainActivity.getNavController().navigate(
                            R.id.action_frontPageFragment_to_recipeMainFragment,
                            bundle);
                }
            });
        }
    }

    public void recipeButtons() {
        mMainActivity.getNetworkManager().getTrendyRecipes(new iNetworkCallback<List<Recipe>>() {
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
        recipeButtons();
        return view;
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addListeners();
    }*/
}