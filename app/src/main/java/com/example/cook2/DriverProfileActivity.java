package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DriverProfileActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Driver driver;
    EditText fnInput, lnInput, phoneInput, addressInput, lpInput, vMakeInput, vModelInput, vColorInput;
    String key, userType, firstName, lastName, phone, address, licensePlate, vMake, vModel, vColor;
    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        driver = getIntent().getParcelableExtra("Driver");
        key = driver.getKey();
        Log.d("drivKeyProf", key);
        userType = "Driver";
        fnInput = findViewById(R.id.driverFN);
        lnInput = findViewById(R.id.driverLN);
        phoneInput = findViewById(R.id.driverPhone);
        addressInput = findViewById(R.id.driverAddress);
        lpInput = findViewById(R.id.driverLicenseID);
        vMakeInput = findViewById(R.id.driverVehicleMake);
        vModelInput = findViewById(R.id.driverVehicleModel);
        vColorInput = findViewById(R.id.driverVehicleColor);
        map = new HashMap<>();
    }

    public void driverUpdate(View view) {
        firstName = fnInput.getText().toString();
        lastName = lnInput.getText().toString();
        phone = phoneInput.getText().toString();
        address = addressInput.getText().toString();
        licensePlate = lpInput.getText().toString();
        vMake = vMakeInput.getText().toString();
        vModel = vModelInput.getText().toString();
        vColor = vColorInput.getText().toString();

        if (firstName.isEmpty() &&
                lastName.isEmpty() &&
                phone.isEmpty() &&
                address.isEmpty() &&
                licensePlate.isEmpty() &&
                vMake.isEmpty() &&
                vModel.isEmpty() &&
                vColor.isEmpty()) {
            return;
        }

        if (!firstName.isEmpty()) {
            map.put("firstName", firstName);
            driver.setFirstName(firstName);
        }

        if (!lastName.isEmpty()) {
            map.put("lastName", lastName);
            driver.setLastName(lastName);
        }

        if (!phone.isEmpty()) {
            map.put("phoneNumber", phone);
            driver.setPhoneNumber(phone);
        }

        if (!address.isEmpty()) {
            map.put("address", address);
            driver.setAddress(address);
        }

        if (!licensePlate.isEmpty()) {
            map.put("vehiclePlate", licensePlate);
            driver.setFirstName(licensePlate);
        }

        if (!vMake.isEmpty()) {
            map.put("vehicleMake", vMake);
            driver.setLastName(vMake);
        }

        if (!vModel.isEmpty()) {
            map.put("vehicleModel", vModel);
            driver.setPhoneNumber(vModel);
        }

        if (!vColor.isEmpty()) {
            map.put("vehicleColor", vColor);
            driver.setAddress(vColor);
        }

        // use back button after this
        Util.editProfile(key, userType, map, db);
    }

    public void driverSignOut(View view) {
        Intent loginActivity = new Intent(view.getContext(), loginActivity.class);
        startActivity(loginActivity);
        finishAffinity();
    }

    public void driverProfileToMain(View view) {
//        Intent driverActivity = new Intent(view.getContext(), DriverMainActivity.class);
//        driverActivity.putExtra("Driver", driver);
//        startActivity(driverActivity);
//        finishAffinity();
        finish();
    }

    public void driverCompletedOrders(View view) {
        Intent completeDriverOrdersActivity = new Intent(view.getContext(), CompletedOrdersActivity.class);
        completeDriverOrdersActivity.putExtra("Driver", driver);
        startActivity(completeDriverOrdersActivity);
    }

}
