package com.example.cook2.objects;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Util {

    public static void createCook(Cook cook, FirebaseFirestore db) {
        Cook temp = new Cook(cook);
        System.out.println(cook.getFirstName() + " BRBRBRBRBRBRBR");
        db.collection("Cook").document(cook.address).set(temp);
    }
    public static void updateCook(Cook cook, FirebaseFirestore db) {
        db.collection("Cook").document(cook.address).update(cook.address,cook);
    }

    public static void createFood(Food food, FirebaseFirestore db) {
        db.collection("Food")
                .add(food)
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
    }

    public static void createOrder(Order order1, FirebaseFirestore db) {
        db.collection("Order")
                .add(order1)
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
    }
}
