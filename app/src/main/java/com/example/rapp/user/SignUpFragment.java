package com.example.rapp.user;

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
import com.example.rapp.networking.iNetworkCallback;


public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "SignUpFragment";
    private View view;
    private MainActivity mMainActivity;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mMainActivity = (MainActivity) requireActivity();

        EditText getEmail = (EditText) view.findViewById(R.id.email_input);
        EditText getUsername = (EditText) view.findViewById(R.id.username_input);
        EditText getPassword = (EditText) view.findViewById(R.id.password_input);

        Button signupButton = (Button) view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getEmail.getText().toString();
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();

                mMainActivity.getNetworkManager().signup(email, username, password, new iNetworkCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, "Successfully signed up");
                        Navigation.findNavController(view).navigate(R.id.logInFragment);
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.e(TAG, "Failed to sign up: " + errorString);
                    }
                });
            }
        });

        return view;
    }
}