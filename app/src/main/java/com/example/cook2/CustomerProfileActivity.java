package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CustomerProfileActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Customer customer;
    EditText fnInput, lnInput, phoneInput, addressInput;
    String key, userType, firstName, lastName, phone, address;
    HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        customer = getIntent().getParcelableExtra("Customer");
        Log.d("custProf", customer.getKey());
        key = customer.getKey();
        userType = "Customer";
        fnInput = findViewById(R.id.customerFirstName);
        lnInput = findViewById(R.id.cookProfLastName);
        phoneInput = findViewById(R.id.cookProfPhone);
        addressInput = findViewById(R.id.cookProfAddress);
        map = new HashMap<>();
    }

    public void customerUpdate(View view) {
        firstName = fnInput.getText().toString();
        lastName = lnInput.getText().toString();
        phone = phoneInput.getText().toString();
        address = addressInput.getText().toString();

        if (firstName.isEmpty() && lastName.isEmpty() && phone.isEmpty() && address.isEmpty()) {
            return;
        }

        if (!firstName.isEmpty()) {
            map.put("firstName", firstName);
            customer.setFirstName(firstName);
        }

        if (!lastName.isEmpty()) {
            map.put("lastName", lastName);
            customer.setLastName(lastName);
        }

        if (!phone.isEmpty()) {
            map.put("phoneNumber", phone);
            customer.setPhoneNumber(phone);
        }

        if (!address.isEmpty()) {
            map.put("address", address);
            customer.setAddress(address);
        }

        Util.editProfile(key, userType, map, db);
    }

    public void customerSignOut(View view) {
        Intent loginActivity = new Intent(view.getContext(), loginActivity.class);
        startActivity(loginActivity);
        finishAffinity();
    }

    public void customerProfileToMain(View view) {
//        Intent customerActivity = new Intent(view.getContext(), CustomerMain.class);
//        customerActivity.putExtra("Customer", customer);
//        startActivity(customerActivity);
//        finishAffinity();
        finish();
    }
}
