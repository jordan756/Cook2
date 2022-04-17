package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CookProfileActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cook cook;
    EditText fnInput, lnInput, phoneInput, addressInput;
    String key, userType, firstName, lastName, phone, address;
    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);

        cook = getIntent().getParcelableExtra("Cook");
        key = cook.getKey();
        Log.d("cookKeyProf", key);
        userType = "Cook";
        fnInput = findViewById(R.id.cookProfFirstName);
        lnInput = findViewById(R.id.cookProfLastName);
        phoneInput = findViewById(R.id.cookProfPhone);
        addressInput = findViewById(R.id.cookProfAddress);
        map = new HashMap<>();
    }

    public void cookUpdate(View view) {
        firstName = fnInput.getText().toString();
        lastName = lnInput.getText().toString();
        phone = phoneInput.getText().toString();
        address = addressInput.getText().toString();
        if (firstName.isEmpty() &&
                lastName.isEmpty() &&
                phone.isEmpty() &&
                address.isEmpty()) {
            return;
        }

        if (!firstName.isEmpty()) {
            map.put("firstName", firstName);
            cook.setFirstName(firstName);
        }

        if (!lastName.isEmpty()) {
            map.put("lastName", lastName);
            cook.setLastName(lastName);
        }

        if (!phone.isEmpty()) {
            map.put("phoneNumber", phone);
            cook.setPhoneNumber(phone);
        }

        if (!address.isEmpty()) {
            map.put("address", address);
            cook.setAddress(address);
        }

        Util.editProfile(key, userType, map, db);
    }

    public void cookProfileToMain(View view) {
//        Intent cookActivity = new Intent(view.getContext(), CookMainActivity.class);
//        cookActivity.putExtra("Cook", cookActivity);
//        startActivity(cookActivity);
//        finishAffinity();
        finish();
    }

    public void cookSignOut(View view) {
        Intent loginActivity = new Intent(view.getContext(), loginActivity.class);
        startActivity(loginActivity);
        finishAffinity();
    }

    public void cookCompletedOrders(View view) {
        Intent completedOrdersActivity = new Intent(view.getContext(), CompletedOrdersActivity.class);
        completedOrdersActivity.putExtra("Cook", cook);
        startActivity(completedOrdersActivity);
    }
}