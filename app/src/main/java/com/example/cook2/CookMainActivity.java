package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
//import com.example.cook2.ui.login.LoginActivity;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class CookMainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cook cook;
    Order order;
    Button changeStatus, start_button, end_button, orderDetailsBtn;
    ListView listView;
    ArrayList<Order> orders;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    String key, temp, id, orderKey;
    String[] orderValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        changeStatus = findViewById((R.id.toggleButton));
        start_button = findViewById(R.id.start_button);
        end_button = findViewById(R.id.end_button);
        orderDetailsBtn = findViewById(R.id.orderDetailsButton);
        cook = getIntent().getExtras().getParcelable("Cook");
        key = cook.getKey();
        final DocumentReference docRef = db.collection("Cook").document(key);
        orders = Util.getAllOrders(cook.getOrders(),db);

        if (cook.open) {
            temp = "Availability: Open";
            changeStatus.setText(temp);
        } else {
            temp = "Availability: Closed";
            changeStatus.setText(temp);
        }

        listView = (ListView) findViewById(R.id.listOrders);
        listView.setItemsCanFocus(false);

        if (cook.getOrders() == null) {
            System.out.println("NULL ORDERS");
            return;
        }

        cook.print();
        arrayList = new ArrayList<>();
        for(Order x : orders) {
            if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                arrayList.add(x.summary());
            }
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    cook = snapshot.toObject(Cook.class);
                    orders = Util.getAllOrders(cook.getOrders(),db);
                    arrayList.clear();
                    for(Order x : orders) {
                        if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                            arrayList.add(x.summary());
                        }
                    }
                    listView.setAdapter(adapter);

                } else {
                    System.out.println("4");
                }
            }
        });
    }

    public void editMenu(View v) {
        Intent i = new Intent(this, CookUpdateMenu.class);
        i.putExtra("Cook",cook);
        startActivity(i);
    }


    public void cookProfileEvent(View v) {
//        Intent i = new Intent(this, CookProfile.class);
//        i.putExtra("Cook",cook);
//        startActivity(i);
    }


    public void startOrderEvent(View v) {
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                temp = listView.getItemAtPosition(i).toString();
                orderValues = temp.split("  -  ");
                id = (orderValues[2]);
                for (Order order: orders) {
                    if (order.orderKey.equals(id)) {
                        if (order.status.equals("unaccepted_cook")) {
                            order.updateStatus();
                            Util.setOrder(order,db);
                        }
                    }
                }
            }
        }

        arrayList.clear();
        for(Order x : orders) {
            if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                arrayList.add(x.summary());
            }
        }
        listView.setAdapter(adapter);
    }


    public void endOrderEvent(View v) {
        for (int i = 0; i < listView.getCount(); i++) {
            if (listView.isItemChecked(i)) {
                temp = listView.getItemAtPosition(i).toString();
                orderValues = temp.split("  -  ");
                id = (orderValues[2]);
                for (Order order: orders) {
                    if (order.orderKey.equals(id)) {
                        if (order.status.equals("accepted_cook")) {
                            order.updateStatus();
                            Util.setOrder(order, db);
                        }
                    }
                }
            }
        }

        arrayList.clear();
        for(Order x : orders) {
            if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                arrayList.add(x.summary());
            }
        }
        listView.setAdapter(adapter);
    }


    public void toggleButtonEvent(View v) {
        cook.changeOpen();
        Util.setCook(cook,db);
        if (cook.open) {
            temp = "Availability: Open";
            changeStatus.setText(temp);
        } else {
            temp = "Availability: Closed";
            changeStatus.setText(temp);
        }
    }

    public void cookOrderDetails(View v) {
        for (int i = 0; i < listView.getCount(); i++) {
            ArrayList<String> ordersList = new ArrayList<String>();
            if (listView.isItemChecked(i)) {
                temp = listView.getItemAtPosition(i).toString();
                orderValues = temp.split("  -  ");
                orderKey = (orderValues[2]);
                order = Util.getOrder(orderKey, db);
                for (Food e : order.foods) {
                    ordersList.add("Dish: " + e.name + "\n" + "Cost: $" + e.cost + "\n"
                            + "Time: " + e.estimatedCookTime.getHours() + "Hr "
                            + e.estimatedCookTime.getMinutes() + "Min");
                }

                Intent x = new Intent(v.getContext(), OrderDetailsActivity.class);
                x.putExtra("DetailsList", ordersList);
                startActivity(x);
                break;
            }
        }
    }
}