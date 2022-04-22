package com.example.rapp.page;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.networking.iNetworkCallback;


public class PageCreateFragment extends Fragment {

    private static final String TAG = "CreatePageFragment";
    private View view;
    private MainActivity mMainActivity;

    public PageCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_create, container, false);
        mMainActivity = (MainActivity) requireActivity();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("LoggedIn", "unavailable");
        if(username == "unavailable") {
            mMainActivity.getNavController().navigate(R.id.logInFragment);
            return view;
        }
        EditText getTitle = (EditText) view.findViewById(R.id.pageTitle_input);
        EditText getDesc = (EditText) view.findViewById(R.id.pageDescription_input);

        Button createPageButton = (Button) view.findViewById(R.id.createPage_button);
        createPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pageTitle = getTitle.getText().toString();
                String pageDesc = getDesc.getText().toString();
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                String username = sharedPref.getString("LoggedIn", "unavailable");

                mMainActivity.getNetworkManager().createPage(pageTitle, pageDesc, username, new iNetworkCallback<Page>() {
                    @Override
                    public void onSuccess(Page result)
                    {
                        Navigation.findNavController(view).navigate(R.id.userMainFragment);
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.e(TAG, "Failed to upload page: " + errorString);
                    }
                });
            }
        });
        return view;
    }
}