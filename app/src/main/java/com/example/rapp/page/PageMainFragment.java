package com.example.rapp.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.networking.iNetworkCallback;

public class PageMainFragment extends Fragment {

    private View view;
    private static final String TAG = "PageMainFragment";
    private Page mPage;
    private MainActivity mMainActivity;

    public PageMainFragment() {
        // Required empty public constructor
    }

    private void setupPage() {
        TextView title = view.findViewById(R.id.page_main_title);
        TextView description = view.findViewById(R.id.page_main_description);
        mMainActivity.getNetworkManager().getPageById(getArguments().getLong("pageId"),
                new iNetworkCallback<Page>() {
                    @Override
                    public void onSuccess(Page result) {
                        mPage = result;
                        title.setText(mPage.getTitle());
                        description.setText(mPage.getDescription());
                    }
                    @Override
                    public void onFailure(String errorString) {
                        Log.e(TAG, "Failed to get pages: " + errorString);
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
        view = inflater.inflate(R.layout.fragment_page_main, container, false);
        mMainActivity = (MainActivity) requireActivity();
        setupPage();
        return view;
    }
}