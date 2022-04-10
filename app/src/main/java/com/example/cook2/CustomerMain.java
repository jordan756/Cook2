package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class CustomerMain extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        ListView listView = (ListView) findViewById(R.id.listOrders);

        customer = getIntent().getParcelableExtra("Customer");
        customer = Util.getCustomer(customer.getKey(),db);
        Log.d("cust", "Name: " + customer.getFirstName());

        ArrayList<Order> orders = Util.getAllOrders(customer.getOrders(), db);
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> adapter;

        for (Order order : orders) {
            arrayList.add(order.summary());
        }

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

                        ArrayList<Order> orders = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                                orders.add(doc.toObject(Order.class));
                        }

                        arrayList.clear();
                        for (Order order : orders) {
                            arrayList.add(order.summary());
                        }

                        listView.setAdapter(adapter);
                    }
                });

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
}