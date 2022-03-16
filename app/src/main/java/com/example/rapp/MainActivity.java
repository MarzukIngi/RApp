package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mRecipesButton;
    private Button mSignUpButton;
    private Button mLogInButton;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecipesButton = (Button) findViewById(R.id.recipes_button);
        mRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipeMainActivity.class);
                startActivity(intent);
            }
        });
        mSignUpButton = (Button) findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        mLogInButton = (Button) findViewById(R.id.login_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, LogInActivity.class);
               startActivity(intent);
            }
        });
    }
}