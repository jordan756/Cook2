package com.example.cook2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class MainActivity4 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String firstName,lastName, email, password, address, phoneNumber, docID, key;
    EditText fNInput, lNInput, eInput, pInput, aInput, pNInput;
    HashMap<String, String> myDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }

    public boolean getData() {
        fNInput = findViewById(R.id.first_name);
        lNInput = findViewById(R.id.last_name);
        eInput = findViewById(R.id.email);
        pInput = findViewById(R.id.pw);
        aInput = findViewById(R.id.ad);
        pNInput = findViewById(R.id.phone);

        firstName = fNInput.getText().toString();
        lastName = lNInput.getText().toString();
        email = eInput.getText().toString();
        password = pInput.getText().toString();
        address = aInput.getText().toString();
        phoneNumber = pNInput.getText().toString();
        Log.d("register", firstName);
        Log.d("register", lastName);
        Log.d("register", email);
        Log.d("register", password);
        Log.d("register", phoneNumber);

        if (TextUtils.isEmpty(firstName) | TextUtils.isEmpty(lastName) | TextUtils.isEmpty(email)
                | TextUtils.isEmpty(password) | TextUtils.isEmpty(address) | TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        key = email.concat(password);
        myDoc = new HashMap<String, String>();
        myDoc.put("firstName", firstName);
        myDoc.put("lastName", lastName);
        myDoc.put("email", email);
        myDoc.put("password", password);
        myDoc.put("address", address);
        myDoc.put("phoneNumber", phoneNumber);
        myDoc.put("userTypeKey", key);
        myDoc.put("key", key);
        return true;
    }

    public void setErrors() {
        fNInput.setError("Cannot be empty.");
        lNInput.setError("Cannot be empty.");
        eInput.setError("Cannot be empty.");
        pInput.setError("Cannot be empty.");
        aInput.setError("Cannot be empty.");
        pNInput.setError("Cannot be empty.");
    }

    public void registerAsCook(View v) {
        boolean g = getData();

        if (!g) {
            setErrors();
        } else {
            myDoc.put("userType", "Cook");
            db.collection("Person").document(key).set(myDoc);
            Cook cook = new Cook(myDoc);
            Util.setCook(cook, db);
            Intent cookActivity = new Intent(v.getContext(), MainActivity.class);
            cookActivity.putExtra("Cook", cook);
            startActivity(cookActivity);
        }
    }

    public void registerAsCustomer(View v) {
        boolean g = getData();
        if (!g) {
            setErrors();
        } else {
            myDoc.put("userType", "Customer");
            db.collection("Person").document(key).set(myDoc);
            Customer customer = new Customer(myDoc);
            Util.setCustomer(customer, db);
            Intent customerActivity = new Intent(v.getContext(), CustomerMain.class);
            customerActivity.putExtra("Customer",customer);
            startActivity(customerActivity);
        }
    }

    public void registerAsDriver(View v) {
        boolean g = getData();
        if (!g) {
            setErrors();
        } else {
            myDoc.put("userType", "Driver");
            db.collection("Person").document(key).set(myDoc);
//            Driver driver = new Driver(myDoc);
//            Util.setDriver(driver, db);
//            Intent driverActivity = new Intent(v.getContext(), DriverMain.class);
//            driverActivity.putExtra("Driver",driver);
//            startActivity(driverActivity);
        }
    }
}