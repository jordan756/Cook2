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
import android.widget.TextView;

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

public class DriverMainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button1, button2, button3, button4;
    TextView addressText;
    ArrayList<Order> orders, orders2;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    ListView driverOrdersList;
    ListView driverOrdersAcceptedList;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList2;
    Driver driver;
    ExecutorService updateLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        button1 = findViewById(R.id.driverOrderDetailsButton);
        button2 = findViewById(R.id.startOrder);
        button3 = findViewById(R.id.endOrder);
        button4 = findViewById(R.id.profileButton);
        driver = getIntent().getExtras().getParcelable("Driver");
        driverOrdersList = findViewById(R.id.listMenu);
        driverOrdersAcceptedList = findViewById(R.id.listOrderItems);
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        updateLists = Executors.newSingleThreadExecutor();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        driverOrdersList.setAdapter(adapter);
        driverOrdersAcceptedList.setAdapter(adapter2);

        driverOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

        driverOrdersAcceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

        updateLists.submit(new updateRight());

        driverOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

        driverOrdersAcceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });

        db.collection("Order")
                .whereEqualTo("status", "finished_cook")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        synchronized (this) {
                            updateLists.submit(new deliverAsync(value));
                        }
                    }
                });
    }

    //TASK THAT UPDATES LEFT SIDE LIST DEPENDING ON SNAPSHOT//
    //WILL BE CALLED ON ANY CHANGE TO LIST ON LEFT
    private class deliverAsync implements Callable {

        private QuerySnapshot value;
        deliverAsync(QuerySnapshot value) {
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

            return null;
        }
    }
    private class updateRight implements Callable {

        @Override
        public Object call() throws Exception {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            orders2 = Util.getAllOrders(driver.getOrderIds(),db);
            arrayList2.clear();
            for (Order x : orders2) {
                if (x == null) {
                    continue;
                }
                arrayList2.add(x.summary2());
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //listView.setAdapter(adapter);
                    adapter2.notifyDataSetChanged();
                }
            });

            return null;
        }
    }




    public void startOrder(View view) {
        for (int i = 0; i < driverOrdersList.getCount(); i++) {
            if (driverOrdersList.isItemChecked(i)) {
                String temp = driverOrdersList.getItemAtPosition(i).toString();
                String[] orderValues = temp.split("  -  ");
                String orderKey = orderValues[2];
                Order order = Util.getOrder(orderKey, db);
                order.setDriverKey(driver.getKey());
                order.updateStatus();
                Util.setOrder(order, db);
                driver.getOrderIds().add(order.orderKey);
            }
        }

        Util.setDriver(driver, db);
        updateLists.submit(new updateRight());
    }


    public void endOrder(View view) {
        for (int i = 0; i < driverOrdersAcceptedList.getCount(); i++) {
            if (driverOrdersAcceptedList.isItemChecked(i)) {
                String temp = driverOrdersAcceptedList.getItemAtPosition(i).toString();
                String[] orderValues = temp.split("  -  ");
                String orderKey = orderValues[2];
                Order order = Util.getOrder(orderKey, db);
                Util.removeOrder(order, driver, db);
            }
        }
        updateLists.submit(new updateRight());
        /*
        orders2 = Util.getAllOrders(driver.getOrderIds(), db);
        arrayList2.clear();
        for (Order x : orders2) {
            if (x == null) {
                continue;
            }
            arrayList2.add(x.summary2());
        }
        driverOrdersAcceptedList.setAdapter(adapter2);

         */
    }


    public void profileButtonEvent(View v) {
        Intent profileActivity = new Intent(v.getContext(), DriverProfileActivity.class);
        profileActivity.putExtra("Driver", driver);
        startActivity(profileActivity);
    }


    public void driverOrderDetails (View v) {
        synchronized (this) {

            if (driverOrdersList.getCount() > 0) {
                for (int i = 0; i < driverOrdersList.getCount(); i++) {
                    ArrayList<String> orderDetails = new ArrayList<String>();
                    if (driverOrdersList.isItemChecked(i)) {
                        String temp = driverOrdersList.getItemAtPosition(i).toString();
                        String[] orderValues = temp.split("  -  ");
                        String orderKey = (orderValues[2]);
                        Order order = Util.getOrder(orderKey, db);
                        Customer customer = Util.getCustomer(order.getCustomerKey(), db);
                        Cook cook = Util.getCook(order.getCookKey(), db);

                        orderDetails.add("Order Status: " + order.getStatus());
                        orderDetails.add("Total Cost: $" + order.totalCost());
                        orderDetails.add("Driver Name: " + driver.getFirstName() + " " + driver.getLastName());
                        orderDetails.add("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
                        orderDetails.add("Customer Address: " + customer.getAddress());
                        orderDetails.add("Cook Name: " + cook.getFirstName() + " " + cook.getLastName());
                        orderDetails.add("Cook Address: " + cook.getAddress());

                        for (Food food : order.foods) {
                            orderDetails.add("Dish: " + food.name + "\n" +
                                    "Cost: $" + food.cost + "\n" +
                                    "Time: " + food.estimatedCookTime.getHours() + "Hr "
                                    + food.estimatedCookTime.getMinutes() + "Min");
                        }

                        Intent x = new Intent(v.getContext(), DriverOrderDetailsActivity.class);
                        x.putExtra("DetailsList", orderDetails);
                        startActivity(x);
                        break;
                    }
                }
            }


            if (driverOrdersAcceptedList.getCount() > 0) {
                for (int i = 0; i < driverOrdersAcceptedList.getCount(); i++) {
                    ArrayList<String> orderDetails = new ArrayList<String>();
                    if (driverOrdersAcceptedList.isItemChecked(i)) {
                        String temp = driverOrdersAcceptedList.getItemAtPosition(i).toString();
                        String[] orderValues = temp.split("  -  ");
                        String orderKey = (orderValues[2]);
                        Order order = Util.getOrder(orderKey, db);
                        Customer customer = Util.getCustomer(order.getCustomerKey(), db);
                        Cook cook = Util.getCook(order.getCookKey(), db);

                        orderDetails.add("Order Status: " + order.getStatus());
                        orderDetails.add("Total Cost: $" + order.totalCost());
                        orderDetails.add("Driver Name: " + driver.getFirstName() + " " + driver.getLastName());
                        orderDetails.add("Customer Name: " + customer.getFirstName() + " " + customer.getLastName());
                        orderDetails.add("Customer Address: " + customer.getAddress());
                        orderDetails.add("Cook Name: " + cook.getFirstName() + " " + cook.getLastName());
                        orderDetails.add("Cook Address: " + cook.getAddress());

                        for (Food food : order.foods) {
                            orderDetails.add("Dish: " + food.name + "\n" +
                                    "Cost: $" + food.cost + "\n" +
                                    "Time: " + food.estimatedCookTime.getHours() + "Hr "
                                    + food.estimatedCookTime.getMinutes() + "Min");
                        }

                        Intent x = new Intent(v.getContext(), DriverOrderDetailsActivity.class);
                        x.putExtra("DetailsList", orderDetails);
                        startActivity(x);
                        break;
                    }
                }
            }

        }
    }


    private void navigation(int ind) {
        for (int i = 0; i < driverOrdersAcceptedList.getCount(); i++) {
            if (driverOrdersAcceptedList.isItemChecked(i)) {
                String temp = driverOrdersAcceptedList.getItemAtPosition(i).toString();
                String[] orderValues = temp.split("  -  ");
                String addresses = (orderValues[3]);
                //System.out.println("orderVal3: " + orderValues[3]);
                String[] addressesSplit = addresses.split("-");
                //System.out.println("Addresses Split: " + addressesSplit[1]);
                // int ind1 = orderValues[3].indexOf("-");

                String colon = ":";
                int index = addressesSplit[ind].indexOf(colon);
                String individualAddress = addressesSplit[ind].substring(index+1 + colon.length());
               // System.out.println("cook add: " + cookAddress);
                System.out.println("ind Add:" + individualAddress);
                Intent mapsActivity = new Intent(getApplicationContext(), DriverMapsActivity.class);
                mapsActivity.putExtra("Address", individualAddress);
                startActivity(mapsActivity);
                return;
            }
        }
    }
    public void navigateCook(View view) {
        navigation(0);
    }

    public void navigateCustomer(View view) {
        navigation(1);
    }
}
