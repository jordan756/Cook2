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

        Button viewCooks = findViewById(R.id.viewCooks);
        customer = getIntent().getParcelableExtra("Customer");
        customer = Util.getCustomer(customer.getKey(),db);
        ArrayList<Order> orders = Util.getAllOrders(customer.getOrders(),db);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> adapter;

        ListView listView = (ListView) findViewById(R.id.listOrders);
        for (Order order : orders) {
            arrayList.add(order.summary());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);



        viewCooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerMain.this, CustomerViewCooksActivity.class);
                i.putExtra("Customer",customer);
                startActivity(i);
            }
        });

        db.collection("Order")
                .whereEqualTo("customerKey", customer.getKey())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                           // Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        ArrayList<Order> orders = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                          //  if (doc.get("name") != null) {
                                orders.add(doc.toObject(Order.class));
                               // orders.add(doc.getString("name"));
                          //  }
                        }
                        arrayList.clear();
                        for (Order order : orders) {
                            arrayList.add(order.summary());
                        }
                        listView.setAdapter(adapter);
                        //Log.d(TAG, "Current cites in CA: " + cities);


                    }
                });

    }


}