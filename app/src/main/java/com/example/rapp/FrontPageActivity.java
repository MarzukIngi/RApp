package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontPageActivity extends AppCompatActivity {

    private Button mUserMainButton;
    private Button mPageMainButton;
    private Button mPageCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mUserMainButton = (Button) findViewById(R.id.user_main_button);
        mUserMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, UserMainActivity.class);
                startActivity(intent);
            }
        });

        mPageMainButton = (Button) findViewById(R.id.page_main_button);
        mPageMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, PageMainActivity.class);
                startActivity(intent);
            }
        });

        mPageCreateButton = (Button) findViewById(R.id.page_create_button);
        mPageCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPageActivity.this, PageCreateActivity.class);
                startActivity(intent);
            }
        });
    }
}