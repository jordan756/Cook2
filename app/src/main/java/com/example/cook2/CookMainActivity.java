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
    ArrayList<Order> orders; //THREADING
    ArrayList<String> arrayList;// = new ArrayList<>(); //NOW A VECTOR TO BE THREAD SAFE
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_main);
        Button changeStatus = findViewById((R.id.toggleButton));
        Button start_button = findViewById(R.id.start_button);
        Button end_button = findViewById(R.id.end_button);
        Button orderDetailsBtn = findViewById(R.id.orderDetailsButton);
        listView = (ListView) findViewById(R.id.listOrders);
        listView.setItemsCanFocus(false);
        //String key = getIntent().getExtras().getString("key");
        cook = getIntent().getExtras().getParcelable("Cook");
        //HAVE TO DO THIS TO GET UPDATED COOK FROM EDIT MENU
        String key = cook.getKey();
        final DocumentReference docRef = db.collection("Cook").document(key);
       // cook = Util.getCook(key,db);





        String temp;
        if (cook.open) {
            temp = "Availibility: Open";
            changeStatus.setText(temp);
        } else {
            temp = "Availibility: CLosed";
            changeStatus.setText(temp);
        }


         //updateOrders();   //THREADDDD

        //ArrayList<String> arrayList;




        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        String[] items = {"Example Order 1 (When we get database, we can remove these)", "Example Order 2", "Example Order 3"};
       // arrayList = new ArrayList<>(Arrays.asList(items));
        if (cook.getOrders() == null) {
            System.out.println("NULL ORDERS");
            return;
        }

        cook.print();

        arrayList = new ArrayList<>();
        /*
        ///orders = Util.getAllOrders(cook.getOrders(),db);
        for(Order x : orders) {
            if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                arrayList.add(x.summary());
            }
        }

         */

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        //System.out.println(listView);
        //System.out.println(adapter);
        //updateOrders(); // MULTITHREADED


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);


                //System.out.println(position);
                //System.out.println("bruh");
            }
        });

       // Cook testCook = (Cook) getIntent().getParcelableExtra("testCook");

        System.out.println(cook.getFirstName() + " " + cook.getLastName());
        System.out.println("size of list" + cook.getMenu().size());
    //    System.out.println(cook.getMenu().get(3).name);
     //   System.out.println(cook.getMenu().get(1).name);

        /*backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.print
                System.out.println("floating button");
            }
        });*/
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bruh");
                for (int i = 0; i < listView.getCount(); i++) {

                    if (listView.isItemChecked(i)) {
                        String temp = listView.getItemAtPosition(i).toString();

                        String[] orderValues = temp.split("  -  ");

                        String id = (orderValues[2]);
                        System.out.println(id);

                        for (Order order: orders) {
                            System.out.println(order.summary());
                            if (order.orderKey.equals(id)) {
                                if (order.status.equals("unaccepted_cook")) {
                                    order.updateStatus();
                                    Util.setOrder(order,db);
                                }
                            }
                        }
                    }
                    //send cook here
                }

                arrayList.clear();
                for(Order x : orders) {
                    if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                        arrayList.add(x.summary());
                    }

                }


                //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);
               // Util.setCook(cook,db);
            }
        });
        changeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cook.changeOpen();
                Util.setCook(cook,db);
                if (cook.open) {
                    String temp = "Availibility: Open";
                    changeStatus.setText(temp);
                    System.out.println("CHECKED");
                    System.out.println("status:" + cook.open);
                } else {
                    String temp = "Availibility: Closed";
                    changeStatus.setText(temp);

                    System.out.println("UNCHECKED");
                    System.out.println("status:" + cook.open);
                }
        }


        });
        end_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bruh");
                for (int i = 0; i < listView.getCount(); i++) {

                    if (listView.isItemChecked(i)) {
                        String temp = listView.getItemAtPosition(i).toString();
                        String[] orderValues = temp.split("  -  ");

                        String id = (orderValues[2]);
                        System.out.println(id);

                        for (Order order: orders) {
                            System.out.println(order.summary());
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


                //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);
               // Util.setCook(cook,db);
            }
        });

        orderDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listView.getCount(); i++) {

                    ArrayList<String> ordersList = new ArrayList<String>();
                    if (listView.isItemChecked(i)) {
                        String temp = listView.getItemAtPosition(i).toString();
                        String[] orderValues = temp.split("  -  ");

                        String orderKey = (orderValues[2]);
                        //System.out.println("order key: " + orderKey);

                        order = Util.getOrder(orderKey, db);
                        for (Food e : order.foods) {
                            ordersList.add("Dish: " + e.name + "\n" + "Cost: $" + e.cost + "\n" + "Time: " + e.estimatedCookTime.getHours() + "Hr " + e.estimatedCookTime.getMinutes() + "Min");
                            //System.out.println("name is: " + e.name);
                        }
                        Intent x = new Intent(view.getContext(), OrderDetailsActivity.class);
                        x.putExtra("DetailsList", ordersList);
                        startActivity(x);
                        break;
                    }
                }
                // System.out.println()
            }
        });
        //final DocumentReference docRef = db.collection("Cook").document(cook.getKey());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Log.w(TAG, "Listen failed.", e);
                    System.out.println("Listen failed.");
                    System.out.println("1");
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";
                System.out.println("2");

                if (snapshot != null && snapshot.exists()) {
                    System.out.println("3");
                    cook = snapshot.toObject(Cook.class);

                    System.out.println(cook.getFirstName());
                    updateOrders();
                    // WILL REPLACE WILL THREADED VERSION
                    /*
                    orders = Util.getAllOrders(cook.getOrders(),db);
                    arrayList.clear();
                    for(Order x : orders) {
                        if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                            arrayList.add(x.summary());
                        }
                    }
                    */

                    listView.setAdapter(adapter);
                    //  Log.d(TAG, source + " data: " + snapshot.getData());
                } else {
                    System.out.println("4");
                    // Log.d(TAG, source + " data: null");
                }
            }
        });
    }
    //private void

    private synchronized void updateOrders()  { ////THREADING
        new Thread() {
            public void run() {
                try {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    System.out.println(mainHandler);


                    for (int i = 5; i < 8; i++) {
                        Thread.sleep(1000);
                        System.out.println(i);
                    }
                    orders = Util.getAllOrders(cook.getOrders(),db);
                    //arrayList = new ArrayList<>();
                    arrayList.clear();

                    System.out.println(listView);
                    System.out.println(adapter);

                    for(Order x : orders) {
                        System.out.println("loop" + x.summary());
                        if (x.status.equals("unaccepted_cook") || x.status.equals("accepted_cook")) {
                            arrayList.add(x.summary());
                        }

                    }

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                        }
                    });
                    for (int i = 0; i < 3; i++) {
                        Thread.sleep(1000);
                        System.out.println(i);
                    }
                    System.out.println("blah");



                } catch (Exception e) {
                    System.out.println("Update ORDERS FAILED" + e);
                }
            }
        }.start();
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
}