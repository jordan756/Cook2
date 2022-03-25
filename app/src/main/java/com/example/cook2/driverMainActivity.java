package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.security.Key;

public class driverMainActivity extends AppCompatActivity {

    EditText  firstName, lastName, phoneNumber, email, password, vMake, vColor, vPlate;
    Button cdeliveryButton;
    //Switch status3, status;
    TextView driverProfile, infoText;

    //shared preferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_1);

        //initialize (xml)
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

//        vMake = findViewById(R.id.vMake);
//        vColor = findViewById(R.id.vColor);
//        vPlate = findViewById(R.id.vPlate);
//        cdeliveryButton = findViewById(R.id.cdeliveryButton);


//        status3 = findViewById(R.id.status3);
//        status = findViewById(R.id.status);

        driverProfile = findViewById(R.id.driverProfile);
        //infoText = findViewById(R.id.infoText);
        //carInformation = findViewById(R.id.carInformation);

        sharedPreferences = getSharedPreferences("SP_NAME", MODE_PRIVATE);




    }
}
