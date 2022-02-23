package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cook2.ui.login.LoginActivity;

public class driverMainActivity3 extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_3);

        Button buttonOne = findViewById(R.id.backButton2);
        Button buttonTwo = findViewById(R.id.anotherButton);
        Button buttonThree = findViewById(R.id.endButton);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.backButton2:
                Intent intent = new Intent(getApplicationContext(), driverMainActivity2.class);
                startActivity(intent);
                break;
            case R.id.anotherButton:
                Intent intent1 = new Intent(getApplicationContext(), driverMainActivity2.class);
                startActivity(intent1);
                break;
            case R.id.endButton:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}