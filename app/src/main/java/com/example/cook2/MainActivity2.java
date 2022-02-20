package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cook2.ui.login.LoginActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void cookButtonEvent(View v) {
        Log.d("success", "cookButtonEvent");
        Intent cookScreen = new Intent(this, LoginActivity.class);
        //v.setEnabled(false);
        //Intent cookScreen = new Intent(this, cookActivity.class);
        startActivity(cookScreen);


        /*
        // control others things by id, button casting
        View driverButton = findViewById(R.id.driverButton1);
        driverButton.setEnabled(false);
        Button bDriver = (Button) driverButton;
        bDriver.setText("Disabled");
        */

        // Button b = (Button) v;
        // b.setText("Disabled");
    }

    public void driverButtonEvent(View v) {
        Log.d("success", "driverButtonEvent");
        v.setEnabled(false);
        Intent driverScreen = new Intent(this, driverMainActivity.class);
        startActivity(driverScreen);
    }

    public void customerButtonEvent(View v) {
        Log.d("success", "customerButtonEvent");
        Intent customerScreen = new Intent(this, LoginActivity.class);
        //v.setEnabled(false);
        //Intent customerScreen = new Intent(this, customerActivity.class);
        startActivity(customerScreen);
    }
}