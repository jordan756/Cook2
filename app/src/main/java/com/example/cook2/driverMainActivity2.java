package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class driverMainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_2);

        Button button1 = findViewById(R.id.startButton);
        Button button2 = findViewById(R.id.Bbutton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.startButton:
                Intent intent = new Intent(getApplicationContext(), driverMainActivity3.class);
                startActivity(intent);
                break;
            case R.id.Bbutton:
                Intent intent1 = new Intent(getApplicationContext(), driverMainActivity.class);
                startActivity(intent1);
                break;

        }
    }
}