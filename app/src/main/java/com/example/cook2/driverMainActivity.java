package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class driverMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_1);

        Button button1 = findViewById(R.id.cdeliveryButton);
        Button button2 = findViewById(R.id.backButton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.cdeliveryButton:
                Intent intent = new Intent(getApplicationContext(), driverMainActivity3.class);
                startActivity(intent);
                break;
            case R.id.backButton:
                Intent intent1 = new Intent(getApplicationContext(), driverMainActivity.class);
                startActivity(intent1);
                break;

        }
    }
}