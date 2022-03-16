package com.example.rapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PageChangeActivity extends AppCompatActivity {

    private Button mFrontPageButton;
    private Button mPageMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_change);

        mFrontPageButton = (Button) findViewById(R.id.front_page_button);
        mFrontPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageChangeActivity.this, FrontPageActivity.class);
                startActivity(intent);
            }
        });

        mPageMainButton = (Button) findViewById(R.id.page_main_button);
        mPageMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageChangeActivity.this, PageMainActivity.class);
                startActivity(intent);
            }
        });
    }
}