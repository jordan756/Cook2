package com.example.cook2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cook2.ui.login.LoginActivity;
/*
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
*/
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
  //  FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
    }

    public void cookButtonEvent(View v) {
        Log.d("success", "cookButtonEvent");

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);
/*
        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("fbSuccess", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fbSuccess", "Error adding document", e);
                    }
                });

        // Create a new user with a first, middle, and last name
        Map<String, Object> user2 = new HashMap<>();
        user2.put("first", "Alan");
        user2.put("middle", "Mathison");
        user2.put("last", "Turing");
        user2.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user2)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("fbSuccessInsert", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("fbSuccess", "Error adding document", e);
                    }
                });

        // read data
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("fbSuccessRead", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("fbSuccess", "Error getting documents.", task.getException());
                        }
                    }
                });
*/


        Intent cookScreen = new Intent(this, LoginActivity.class);
        //v.setEnabled(false);
        //Intent cookScreen = new Intent(this, cookActivity.class);
        startActivity(cookScreen);


        /*
        // control others things by id, button casting
        View driverButton = findViewById(R.id.driverButton1);
        driverButton.setEnabled(false);
        Button bDriver = (Button) driverButton;
        bDriver.setText("Disabled");
        */

        // Button b = (Button) v;
        // b.setText("Disabled");
    }

    public void driverButtonEvent(View v) {
        Log.d("success", "driverButtonEvent");
       //v.setEnabled(false);
        Intent driverScreen = new Intent(this, driverMainActivity.class);
        startActivity(driverScreen);
    }

    public void customerButtonEvent(View v) {
        Log.d("success", "customerButtonEvent");
        Intent customerScreen = new Intent(this, LoginActivity.class);
        //v.setEnabled(false);
        //Intent customerScreen = new Intent(this, customerActivity.class);
        startActivity(customerScreen);
    }
}