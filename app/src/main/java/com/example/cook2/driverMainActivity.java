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

    EditText  firstName3, lastName3, phoneNumber3, emailAddress3, customerPassword2, vehicleM, vehicleColor, vehiclePlate;
    Button cdeliveryButton, backButton, saveButton;
    Switch status3, status;
    TextView driverProfile;

    //shared preferences
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_1);

        //initialize (xml)
        firstName3 = findViewById(R.id.firstName3);
        lastName3 = findViewById(R.id.lastName3);
        phoneNumber3 = findViewById(R.id.phoneNumber3);
        emailAddress3 = findViewById(R.id.emailAddress3);
        customerPassword2 = findViewById(R.id.customerPassword2);
        vehicleM = findViewById(R.id.vehicleM);
        vehicleColor = findViewById(R.id.vehicleColor);
        vehiclePlate = findViewById(R.id.vehiclePlate);




        cdeliveryButton = findViewById(R.id.cdeliveryButton);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);

        status3 = findViewById(R.id.status3);
        status = findViewById(R.id.status);

        driverProfile = findViewById(R.id.driverProfile);
        //carInformation = findViewById(R.id.carInformation);

        sharedPreferences = getSharedPreferences("SP_NAME", MODE_PRIVATE);


        //save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input data

                String first = firstName3.getText().toString();
                String last = lastName3.getText().toString();
                int phone = Integer.parseInt(phoneNumber3.getText().toString().trim());
                String email = emailAddress3.getText().toString();
                String password = customerPassword2.getText().toString();
                String vehiclem = vehicleM.getText().toString();


                //edit data (add new vehicle info)
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //putting the data in
                editor.putString("FIRST", first);
                editor.putString("LAST", last);
                editor.putInt("PHONE", phone);
                editor.putString("EMAIL", email);
                editor.putString("PASSWORD", password);
                editor.putString("VEHICLE", vehiclem);

                //apply new changes made to info
                editor.apply();

            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //data from shared preferences (needs work)

                String first = sharedPreferences.getString("FIRST","");
                String last = sharedPreferences.getString("LAST", "");
                int phone = sharedPreferences.getInt("PHONE", 0);
                String email = sharedPreferences.getString("EMAIL","");
                String password = sharedPreferences.getString("PASSWORD", "");
                String vehiclem = sharedPreferences.getString("VEHICLE","");

                //show data (needs work)
                saveButton.setText("Name: " +first +last
                            +"\nPhone Number:" +phone
                            +"\nEmail: " +email
                            +"\nAPassword" +password
                            +"\nVehicle Info: " +vehiclem);

            }

        });

    }
}
