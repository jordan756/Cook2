package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
    }
    public void launchOrderDetails(View view) {
        Intent i = new Intent(this, CustomerProgressActivity.class);
        startActivity(i);
    }

    public void launchCooksMenus(View view) {
        Intent i = new Intent(this, CustomerViewCooksActivity.class);
        startActivity(i);
    }
}