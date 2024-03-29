package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
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
import java.util.Vector;

// cook main activity
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

        if (cook.open) {
            temp = "Availability: Open";
            changeStatus.setText(temp);
        } else {
            temp = "Availability: Closed";
            changeStatus.setText(temp);
        }

        listView = (ListView) findViewById(R.id.listOrders);
        listView.setItemsCanFocus(false);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        if (cook.getOrders() == null) {
            System.out.println("NULL ORDERS");
            return;
        }

        cook.print();

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
                    updateOrders();
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
        Intent profileActivity = new Intent(v.getContext(), CookProfileActivity.class);
        profileActivity.putExtra("Cook", cook);
        startActivity(profileActivity);
    }


    public void startOrderEvent(View v) {
        synchronized (this) {
           // sleeper();
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    temp = listView.getItemAtPosition(i).toString();
                    orderValues = temp.split("  -  ");
                    id = (orderValues[2]);
                    for (Order order : orders) {
                        if (order.orderKey.equals(id)) {
                            if (order.status.equals("unaccepted_cook")) {
                                order.updateStatus();
                                Util.setOrder(order, db);
                            }
                        }
                    }
                }
            }

            arrayList.clear();
            for (Order x : orders) {
                if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                    arrayList.add(x.summary());
                }
            }
            listView.setAdapter(adapter);
        }
    }


    public void endOrderEvent(View v) {
        synchronized (this) {
            //sleeper();
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    temp = listView.getItemAtPosition(i).toString();
                    orderValues = temp.split("  -  ");
                    id = (orderValues[2]);
                    for (Order order : orders) {
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
            for (Order x : orders) {
                if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                    arrayList.add(x.summary());
                }
            }
            listView.setAdapter(adapter);
        }
    }


    public void toggleButtonEvent(View v) {
        cook.changeOpen();
        Util.setCook(cook,db);
        if (cook.open) {
            temp = "Availability: Open";
        } else {
            temp = "Availability: Closed";
        }
        changeStatus.setText(temp);
    }


    public void cookOrderDetails(View v) {
        for (int i = 0; i < listView.getCount(); i++) {
            ArrayList<String> orderDetails = new ArrayList<String>();
            if (listView.isItemChecked(i)) {
                temp = listView.getItemAtPosition(i).toString();
                orderValues = temp.split("  -  ");
                orderKey = (orderValues[2]);
                order = Util.getOrder(orderKey, db);
                Customer customer = Util.getCustomer(order.getCustomerKey(), db);
                orderDetails.add("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
                orderDetails.add("Cook Name: " + cook.getFirstName() + " " + cook.getLastName());
                orderDetails.add("Total Cost: $" + order.totalCost());

                for (Food food : order.foods) {
                    orderDetails.add("Dish: " + food.name + "\n" +
                            "Cost: $" + food.cost + "\n" +
                            "Time: " + food.estimatedCookTime.getHours() + "Hr "
                            + food.estimatedCookTime.getMinutes() + "Min");
                }

                Intent x = new Intent(v.getContext(), CookOrderDetailsActivity.class);
                x.putExtra("DetailsList", orderDetails);
                startActivity(x);
                break;
            }
        }
    }


    // THREADING METHOD
    private void updateOrders()  {
        new Thread() {
            public void run() {

                synchronized (this) {
                    //System.out.println("in sync");
                    try {
                        Handler mainHandler = new Handler(Looper.getMainLooper());


                        orders = Util.getAllOrders(cook.getOrders(), db);
                        //arrayList.clear();

                        //need to test if new incoming orders effect what the user has selected
                        for (Order x : orders) {
                            if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                                if (!arrayList.contains(x.summary())) {
                                    arrayList.add(x.summary());
                                }
                            }
                        }

                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });


                    } catch (Exception e) {
                        System.out.println("Update ORDERS FAILED" + e);
                    }
                }
              //  System.out.println("out sync");
            }
        }.start();
    }

}