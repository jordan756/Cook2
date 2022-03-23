package com.example.cook2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity4 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String firstName,lastName, email, password, address, phoneNumber, docID;
    EditText fNInput, lNInput, eInput, pInput, aInput, pNInput;
    HashMap<String, Object> myDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        myDoc = new HashMap<String, Object>();
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
        Log.w("register", firstName);
        Log.w("register", lastName);
        Log.w("register", email);
        Log.w("register", password);
        Log.w("register", phoneNumber);

        if (TextUtils.isEmpty(firstName) | TextUtils.isEmpty(lastName) | TextUtils.isEmpty(email)
                | TextUtils.isEmpty(password) | TextUtils.isEmpty(address) | TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        myDoc.clear();
        myDoc.put("firstName", firstName);
        myDoc.put("lastName", lastName);
        myDoc.put("email", email);
        myDoc.put("password", password);
        myDoc.put("address", address);
        myDoc.put("phoneNumber", phoneNumber);
        myDoc.put("numberOfRatings", 0);
        myDoc.put("currentRating", 0);
        myDoc.put("userTypeKey", email.concat(password));
        myDoc.put("key", email.concat(password));

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
            db.collection("Person").document(email.concat(password)).set(myDoc);
            //db.collection("Cook").document(email.concat(password)).set(myDoc);
        }
    }

    public void registerAsCustomer(View v) {
        boolean g = getData();
        if (!g) {
            setErrors();
        } else {
            myDoc.put("userType", "Customer");
            db.collection("Person").document(email.concat(password)).set(myDoc);
            //db.collection("Customer").document(email.concat(password)).set(myDoc);
        }
    }

    public void registerAsDriver(View v) {
        boolean g = getData();
        if (!g) {
            setErrors();
        } else {
            myDoc.put("userType", "Driver");
            db.collection("Person").document(email.concat(password)).set(myDoc);
            //db.collection("Driver").document(email.concat(password)).set(myDoc);
        }
    }
}