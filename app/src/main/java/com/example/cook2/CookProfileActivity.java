package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CookProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);
    }

    public void cookProfileToMain(View view) {
    }

    public void cookSignOut(View view) {
        Intent loginActivity = new Intent(view.getContext(), loginActivity.class);
        startActivity(loginActivity);
        finishAffinity();
    }
}