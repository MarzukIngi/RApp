package com.example.rapp.user;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.entities.Page;
import com.example.rapp.networking.iNetworkCallback;

import org.json.JSONException;

import java.util.List;

public class UserMainFragment extends Fragment {

    private static final String TAG = "UserMainFragment";
    private View mView;
    private MainActivity mMainActivity;
    private List<Page> mPages;

    public UserMainFragment() {
        // Required empty public constructor
    }

    private void setupButtons() throws JSONException {
        LinearLayout layout = mView.findViewById(R.id.user_main_layout);
        TextView tw = mView.findViewById(R.id.username_textview);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("LoggedIn", "unavailable");
        if(username == "unavailable") {
            mMainActivity.getNavController().navigate(R.id.logInFragment);
            return;
        }
        tw.setText(username);
        mMainActivity.getNetworkManager().getPagesByUsername(username, new iNetworkCallback<List<Page>>() {
            @Override
            public void onSuccess(List<Page> result) {
                mPages = result;
                for(Page p: mPages) {
                    Button b = new Button(getContext());
                    b.setText(p.getTitle());
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
                            bundle.putLong("pageId", p.getID());
                            mMainActivity.getNavController().navigate(
                                    R.id.action_userMainFragment_to_pageMainFragment,
                                    bundle
                            );
                        }
                    });
                    layout.addView(b);
                }
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, errorString);
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
        mView = inflater.inflate(R.layout.fragment_user_main, container, false);
        mMainActivity = (MainActivity) requireActivity();

        Button logoutButton = (Button) mView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("LoggedIn", null);
                editor.apply();
                Navigation.findNavController(view).navigate(R.id.logInFragment);
            }
        });
        try {
            setupButtons();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return mView;
    }
}