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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletedOrdersActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Customer customer;
    Cook cook;
    Driver driver;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ListView listView;
    ExecutorService updateList;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_orders);

        customer = getIntent().getExtras().getParcelable("Customer");
        cook = getIntent().getExtras().getParcelable("Cook");
        driver = getIntent().getExtras().getParcelable("Driver");

        listView = (ListView) findViewById(R.id.listCompletedOrders);
        updateList = Executors.newFixedThreadPool(2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });


        if (customer != null) {
            Log.d("COA", "===============customer==============");
            customer = Util.getCustomer(customer.getKey(),db);
            userType = "Customer";
            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);

            db.collection("CompletedOrder")
                    .whereEqualTo("customerKey", customer.getKey())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            synchronized (this) {
                                updateList.submit(new CompletedOrdersActivity.orderAsync(value));
                            }

                        }
                    });


        } else if (cook != null) {
            Log.d("COA", "===============cook==============");
            // myButton.setEnabled(false);
            cook = Util.getCook(cook.getKey(),db);
            userType = "Cook";
            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);

            db.collection("CompletedOrder")
                    .whereEqualTo("cookKey", cook.getKey())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            synchronized (this) {
                                updateList.submit(new CompletedOrdersActivity.orderAsync(value));
                            }
                        }
                    });


        } else if (driver != null) {
            Log.d("COA", "===============driver==============");
            // myButton.setEnabled(false);
            driver = Util.getDriver(driver.getKey(),db);
            userType = "Driver";
            arrayList = new ArrayList<>();
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);
            db.collection("CompletedOrder")
                    .whereEqualTo("driverKey", driver.getKey())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            synchronized (this) {
                                updateList.submit(new CompletedOrdersActivity.orderAsync(value));
                            }

                        }
                    });
        }
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

    public void CORateOrder(View view) {
        synchronized (this) {
            for (int i = 0; i < listView.getCount(); i++) {
                ArrayList<String> orderDetails = new ArrayList<String>();
                if (listView.isItemChecked(i)) {
                    String temp = listView.getItemAtPosition(i).toString();
                    String[] orderValues = temp.split("  -  ");
                    String orderKey = (orderValues[2]);
                    Order order = Util.getCompletedOrder(orderKey, db);
//                    String driverName;
//                    String driverKey = order.getDriverKey();
                    Cook ck = Util.getCook(order.getCookKey(), db);
                    Customer cust = Util.getCustomer(order.getCustomerKey(), db);
                    Driver driv = Util.getDriver(order.getDriverKey(), db);
                    orderDetails.add(userType);
                    orderDetails.add(cust.getKey());
                    orderDetails.add(ck.getKey());
                    orderDetails.add(driv.getKey());
                    orderDetails.add("Customer Name: " + cust.getFirstName() + " " + cust.getLastName());
                    orderDetails.add("Cook Name: " + ck.getFirstName() + " " + ck.getLastName());
                    orderDetails.add("Driver Name: " + driv.getFirstName() + " " + driv.getLastName());
                    orderDetails.add("Total Cost: $" + order.totalCost());

//                    try {
//                        Driver driver = Util.getDriver(driverKey, db);
//                        driverName = driver.getFirstName() + " " + driver.getLastName();
//                    } catch (Exception e) {
//                        driverName = "TBD";
//                    }
//                    orderDetails.add("Driver Name: " + driverName);

                    for (Food food : order.foods) {
                        orderDetails.add("Dish: " + food.name + "\n" +
                                "Cost: $" + food.cost + "\n" +
                                "Time: " + food.estimatedCookTime.getHours() + "Hr "
                                + food.estimatedCookTime.getMinutes() + "Min");
                    }

                    Intent x = new Intent(view.getContext(), RateOrderActivity.class);
                    x.putExtra("DetailsList", orderDetails);
                    startActivity(x);
                    break;
                }
            }
        }
    }

    public void COtoMain(View view) {
//        Intent loginActivity = new Intent(view.getContext(), loginActivity.class);
//        startActivity(loginActivity);
        finish();
    }
}