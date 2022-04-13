package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerMain extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Customer customer;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        ListView listView = (ListView) findViewById(R.id.listOrders);

        customer = getIntent().getParcelableExtra("Customer");
        customer = Util.getCustomer(customer.getKey(),db);

        Log.d("cust", "Name: " + customer.getFirstName());
        ExecutorService updateList = Executors.newFixedThreadPool(2);
       // orderAsync.submit();
      //  ArrayList<Order> orders = Util.getAllOrders(customer.getOrders(), db);
        arrayList = new ArrayList<>();
        //ArrayAdapter<String> adapter;
/*
        for (Order order : orders) {
            arrayList.add(order.summary());
        }
*/
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        db.collection("Order")
                .whereEqualTo("customerKey", customer.getKey())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        synchronized (this) {
                            updateList.submit(new orderAsync(value));
                        }
                        /*
                        ArrayList<Order> orders = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                                orders.add(doc.toObject(Order.class));
                        }

                        arrayList.clear();
                        for (Order order : orders) {
                            arrayList.add(order.summary());
                        }

                        listView.setAdapter(adapter);
                        */

                    }
                });
    }
    private class orderAsync implements Callable {
        private QuerySnapshot value;
        orderAsync(QuerySnapshot value) {
            this.value = value;
        }
        @Override
        public Object call() throws Exception {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            ArrayList<Order> orders = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                orders.add(doc.toObject(Order.class));
            }
            arrayList.clear();
            for (Order order : orders) {
                arrayList.add(order.summary());
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            //adapter.notifyDataSetChanged();
            return null;
        }
    }


    public void customerProfile(View v) {
        Intent profileActivity = new Intent(v.getContext(), CustomerProfileActivity.class);
        profileActivity.putExtra("Customer", customer);
        startActivity(profileActivity);
        // finishAffinity();
    }

    public void viewNearbyCooks(View v) {
        Intent i = new Intent(CustomerMain.this, CustomerViewCooksActivity.class);
        i.putExtra("Customer", customer);
        startActivity(i);
        // finishAffinity();
    }

    public void customerOrderDetails(View v) {
        synchronized (this) {
            //PUT WHERE U SELECT THE ORDER FROM THE LIST HERE
        }
        Intent i = new Intent(CustomerMain.this, CustomerOrderDetailsActivity.class);
        i.putExtra("Customer", customer);
        startActivity(i);
    }


}