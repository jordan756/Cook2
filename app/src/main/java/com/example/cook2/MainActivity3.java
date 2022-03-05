package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void launchCustomerProfile(View v) {
        Intent i = new Intent(this, CustomerProfileActivity.class);
        startActivity(i);
    }
//moved to "CustomerProfileActivity"
/*    public void launchCooksMenus(View v) {
        Intent i = new Intent(this, CustomerViewCooksActivity.class);
        startActivity(i);
    }

    public void launchOrderDetails(View v) {
        Intent i = new Intent(this, CustomerProgressActivity.class);
        startActivity(i);
    }*/
}