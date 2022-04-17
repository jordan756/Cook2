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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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


    public static void removeOrder(Order order, Driver driver, FirebaseFirestore db) {
        db.collection("CompletedOrder").document(order.orderKey).set(order);

        db.collection("Order").document(order.orderKey)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Order removed");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Order remove failed");
                    }
                });

        db.collection("Cook").document(order.cookKey)
                .update("orders" , FieldValue.arrayRemove(order.orderKey))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("cook remove order");
                    }
                });

        db.collection("Customer").document(order.customerKey)
                .update("orders" , FieldValue.arrayRemove(order.orderKey))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Customer remove order");
                    }
                });

        db.collection("Driver").document(driver.getKey())
                .update("orderIds" , FieldValue.arrayRemove(order.orderKey))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Driver remove order");
                    }
                });
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


    public static Driver getDriver(String key, FirebaseFirestore db) {
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
        Driver driver = temp2.toObject(Driver.class);
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
        //System.out.print(order.orderKey);
        return order;
    }


    public static Order getCompletedOrder(String key, FirebaseFirestore db) {
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("CompletedOrder").document(key).get();
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
        //System.out.print(order.orderKey);
        return order;
    }


    public static ArrayList<Order> getAllOrders(ArrayList<String> orderKeys,FirebaseFirestore db) {
        ArrayList<Order> allOrders = new ArrayList<>();
        for (String temp: orderKeys) {
            allOrders.add(getOrder(temp,db));
        }
        return allOrders;
    }


    public static ArrayList<Order> getAllOrdersOpen(FirebaseFirestore db) {
        ArrayList<Order> allOrders = new ArrayList<>();
        Task<QuerySnapshot> bruh = db.collection("Order").whereEqualTo("status","finished_cook").get();
        while(!bruh.isComplete()) {

        }

        List<DocumentSnapshot> temp = bruh.getResult().getDocuments();

        for (DocumentSnapshot doc : temp) {
            allOrders.add(doc.toObject(Order.class));
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
        // System.out.println(allCooks.get(0).firstName + allCooks.get(1).firstName);

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


    public static void editProfile(String key, String userType, HashMap<String, Object> map, FirebaseFirestore db) {
        // update Person
        // adds more data to Person if user is Driver but it won't affect anything
        db.collection("Person").document(key)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Util", "DocumentSnapshot successfully updated!");
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Util", "Error updating document", e);
                    }
                });

        // update userType
        db.collection(userType).document(key)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Util", "DocumentSnapshot successfully updated!");
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Util", "Error updating document", e);
                    }
                });
    }
}
