package com.example.rapp.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;

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

        TextView title = view.findViewById(R.id.page_title);
        TextView description = view.findViewById(R.id.page_title);

        //mMainActivity.getNetworkManager().get
        return view;
    }
}