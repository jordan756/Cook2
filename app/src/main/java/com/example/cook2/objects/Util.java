package com.example.cook2.objects;

import static java.lang.Thread.sleep;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    // USES EMAIL + PASSWORD AS KEY FOR DATABASE
    public static void setCook(Cook cook, FirebaseFirestore db) {
        db.collection("Cook").document(cook.getKey()).set(cook);
    }

    public static void setCustomer(Customer customer, FirebaseFirestore db) {
        db.collection("Customer").document(customer.getKey()).set(customer);
    }

    public static void setDriver(Driver driver, FirebaseFirestore db) {
        db.collection("Driver").document(driver.getKey()).set(driver);
    }

    public static void setOrder(Order order, FirebaseFirestore db) {
        db.collection("Order").document(order.orderKey).set(order);
    }

    public static Customer getCustomer(String key, FirebaseFirestore db) {
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Customer").document(key).get();
        }
        while(!temp.isComplete()) {
            //sleep(1000);
        }
        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }
        // Cook cook = temp2.data();
        Customer customer = temp2.toObject(Customer.class);
        System.out.print(customer.firstName);
        return customer;
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
        Cook cook = temp2.toObject(Cook.class);
        return cook;
    }

    public static Customer getDriver(String key, FirebaseFirestore db) {
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Driver").document(key).get();
        }
        while(!temp.isComplete()) {
            //sleep(1000);
        }
        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }
        Customer driver = temp2.toObject(Customer.class);
        return driver;
    }

    public static Order getOrder(String key, FirebaseFirestore db) {
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Order").document(key).get();
        }
        while(!temp.isComplete()) {
            //sleep(1000);
        }
        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }
        // Cook cook = temp2.data();
        Order order = temp2.toObject(Order.class);
        System.out.print(order.orderKey);
        return order;
    }

    public static ArrayList<Order> getAllOrders(ArrayList<String> orderKeys,FirebaseFirestore db) {
        ArrayList<Order> allOrders = new ArrayList<>();
        for (String temp: orderKeys) {
            allOrders.add(getOrder(temp,db));
        }
        return allOrders;
    }

    public static ArrayList<Order> getAllOrdersCollection(FirebaseFirestore db) {
        Task<QuerySnapshot> temp = null;
        ArrayList<Order> allOrders = new ArrayList<>();
        while(temp == null) {
            temp = db.collection("Order").get();
        }
        while(!temp.isComplete()) { }

        QuerySnapshot temp2 = temp.getResult();

        for (QueryDocumentSnapshot o: temp2) {
            if (o.exists()) {
                Order tempOrder = o.toObject(Order.class);
                allOrders.add(tempOrder);
            }
        }

        return allOrders;
    }

    public static ArrayList<Cook> getAllCooks(FirebaseFirestore db) {
        ArrayList<Cook> allCooks = new ArrayList<>();
        Task<QuerySnapshot> bruh = db.collection("Cook").whereEqualTo("open",true).get();
        while(!bruh.isComplete()) {

        }
        System.out.println(bruh);
        System.out.println(bruh.getResult().size());
        System.out.println(bruh.getResult().getDocuments());
        // ArrayList<DocumentSnapshot> temp = new ArrayList<>();
        // temp.addAll(bruh.getResult().getDocuments());
        List<DocumentSnapshot> temp= bruh.getResult().getDocuments();

        for (DocumentSnapshot doc : temp) {
            allCooks.add(doc.toObject(Cook.class));
        }
        //System.out.println(allCooks.get(0).firstName + allCooks.get(1).firstName);


        return allCooks;
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
