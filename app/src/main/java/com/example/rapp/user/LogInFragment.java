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
import android.widget.EditText;
import android.widget.Toast;

import com.example.rapp.MainActivity;
import com.example.rapp.R;
import com.example.rapp.networking.iNetworkCallback;

import org.json.JSONObject;

public class LogInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "LogInFragment";
    private View view;
    private MainActivity mMainActivity;

    // TODO: Rename and change types of parameters

    public LogInFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
//    public static LogInFragment newInstance(String param1, String param2) {
//        LogInFragment fragment = new LogInFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        mMainActivity = (MainActivity) requireActivity();
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString("LoggedIn", "unavailable");
        if(username != "unavailable") {
            mMainActivity.getNavController().navigate(R.id.frontPageFragment);
            return view;
        }
        EditText getUsername = (EditText) view.findViewById(R.id.username_input);
        EditText getPassword = (EditText) view.findViewById(R.id.password_input);

        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();

                mMainActivity.getNetworkManager().login(username, password, new iNetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals("approved")) {
                            Log.d(TAG, "Successfully logged in");
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("LoggedIn", username);
                            editor.apply();

                            Navigation.findNavController(view).navigate(R.id.frontPageFragment);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.e(TAG, "Failed to log in: " + errorString);
                    }
                });
            }
        });

        Button signupButton = (Button) view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.signUpFragment);
            }
        });

        Button continueButton = (Button) view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.frontPageFragment);
            }
        });

        return view;
    }
}