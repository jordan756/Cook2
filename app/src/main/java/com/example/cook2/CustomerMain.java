package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Food;
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
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        listView = (ListView) findViewById(R.id.listOrders);
        customer = getIntent().getParcelableExtra("Customer");
        customer = Util.getCustomer(customer.getKey(),db);
        Log.d("cust", "Name: " + customer.getFirstName());
        ExecutorService updateList = Executors.newFixedThreadPool(2);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

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
                    adapter.notifyDataSetChanged();
                }
            });
            return null;
        }
    }


    public void customerProfile(View v) {
        Intent profileActivity = new Intent(v.getContext(), CustomerProfileActivity.class);
        profileActivity.putExtra("Customer", customer);
        startActivity(profileActivity);
    }


    public void viewNearbyCooks(View v) {
        Intent i = new Intent(CustomerMain.this, CustomerViewCooksActivity.class);
        i.putExtra("Customer", customer);
        startActivity(i);
    }


    public void customerOrderDetails(View v) {
        synchronized (this) {
            for (int i = 0; i < listView.getCount(); i++) {
                ArrayList<String> orderDetails = new ArrayList<String>();
                if (listView.isItemChecked(i)) {
                    String temp = listView.getItemAtPosition(i).toString();
                    String[] orderValues = temp.split("  -  ");
                    String orderKey = (orderValues[2]);
                    Order order = Util.getOrder(orderKey, db);
                    // Driver driver = Util.getCustomer(order.getCustomerKey(), db);
                    String driverName;
                    //Driver driver;
                    String driverKey = order.getDriverKey();
                    Cook cook = Util.getCook(order.getCookKey(), db);
                    orderDetails.add("Order Status: " + order.getStatus());
                    orderDetails.add("Total Cost: $" + order.totalCost());
                    orderDetails.add("Cook Name: " + cook.getFirstName() + " " + cook.getLastName());
                    orderDetails.add("Cook Address: " + cook.getAddress());

                    try {
                        Driver driver = Util.getDriver(order.getDriverKey(), db);
                        driverName = driver.getFirstName() + " " + driver.getLastName();
                    } catch (Exception e) {
                        driverName = "TBD";
                    }
                    orderDetails.add("Driver Name: " + driverName);

                    for (Food food : order.foods) {
                        orderDetails.add("Dish: " + food.name + "\n" +
                                "Cost: $" + food.cost + "\n" +
                                "Time: " + food.estimatedCookTime.getHours() + "Hr "
                                + food.estimatedCookTime.getMinutes() + "Min");
                    }

                    Intent x = new Intent(v.getContext(), CustomerOrderDetailsActivity.class);
                    x.putExtra("DetailsList", orderDetails);
                    startActivity(x);
                    break;
                }
            }
        }
    }


//    public void customerCompletedOrders(View v) {
//        Intent i = new Intent(CustomerMain.this, CompletedOrdersActivity.class);
//        i.putExtra("Customer", customer);
//        startActivity(i);
//    }
}