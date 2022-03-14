package com.example.cook2.objects;

import static java.lang.Thread.sleep;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Util {

    // USES EMAIL + PASSWORD AS KEY FOR DATABASE
    public static void setCook(Cook cook, FirebaseFirestore db) {

        db.collection("Cook").document(cook.getKey()).set(cook);
    }


    //Might pause program getting results, will need threading
    public static Cook getCook(String key, FirebaseFirestore db) {
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Cook").document(key).get();
        }
        while(!temp.isComplete()) {
            //sleep(1000);
        }
        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }
       // Cook cook = temp2.data();
        Cook cook = temp2.toObject(Cook.class);
        System.out.print(cook.firstName);
        return cook;

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
