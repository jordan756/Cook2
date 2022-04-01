package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class driverMainActivity2 extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button1, button2, button3, button4;
    TextView addressText;
    ArrayList<Order> orders;
    ArrayAdapter<String> adapter;
    ListView driverOrdersList;
    ListView driverOrdersAcceptedList;
    ArrayList<String> arrayList;
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_2);

        button1 = findViewById(R.id.addToOrder);
        button2 = findViewById(R.id.startOrder);
        button3 = findViewById(R.id.endOrder);
        button4 = findViewById(R.id.profileButton);
        addressText = findViewById(R.id.address);
        driver = getIntent().getExtras().getParcelable("Driver");

//        button1.setOnClickListener(this);
//        button2.setOnClickListener(this);
//        button3.setOnClickListener(this);
//        button4.setOnClickListener(this);


        orders = Util.getAllOrdersOpen(db);
        driverOrdersList = findViewById(R.id.listMenu);
        driverOrdersAcceptedList = findViewById(R.id.listOrderItems);
        arrayList = new ArrayList<>();
        for (Order x : orders) {
            arrayList.add(x.summary());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        driverOrdersList.setAdapter(adapter);
        driverOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });



//        orderItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                view.setSelected(true);
//                view.setBackgroundResource(R.drawable.select);
//            }
//        });
    }


    public void startOrder(View view) {
        //        for (int i = 0; i < listView.getCount(); i++) {
//
//            if (listView.isItemChecked(i)) {
//                String temp = listView.getItemAtPosition(i).toString();
//
//                String[] orderValues = temp.split("  -  ");
//
//                String id = (orderValues[2]);
//                System.out.println(id);
//
//                for (Order order: orders) {
//                    System.out.println(order.summary());
//                    if (order.orderKey.equals(id)) {
//                        if (order.status.equals("unaccepted_cook")) {
//                            order.updateStatus();
//                            Util.setOrder(order,db);
//                        }
//                    }
//                }
//            }
//        }
    }

    public void endOrder(View view) {
        //        for (int i = 0; i < listView.getCount(); i++) {
//
//            if (listView.isItemChecked(i)) {
//                String temp = listView.getItemAtPosition(i).toString();
//
//                String[] orderValues = temp.split("  -  ");
//
//                String id = (orderValues[2]);
//                System.out.println(id);
//
//                for (Order order: orders) {
//                    System.out.println(order.summary());
//                    if (order.orderKey.equals(id)) {
//                        if (order.status.equals("unaccepted_cook")) {
//                            order.updateStatus();
//                            Util.setOrder(order,db);
//                        }
//                    }
//                }
//            }
//        }
    }

    public void profileButtonEvent(View view) {
        Intent profileActivity = new Intent(getApplicationContext(), driverMainActivity.class);
        startActivity(profileActivity);
    }

    public void getAddresses(View view) {

        //for (int i = 0; i < listView2.getCount(); i++) {
//
//            if (listView2.isItemChecked(i)) {
//                String temp = listView2.getItemAtPosition(i).toString();
//
//                String[] orderValues = temp.split("  -  ");
//
//                String id = (orderValues[2]);
//                System.out.println(id);
//
//                for (Order order: orders) {
//                    System.out.println(order.summary());
//                    if (order.orderKey.equals(id)) {
//                        if (order.status.equals("unaccepted_cook")) {
//                            order.updateStatus();
//                            Util.setOrder(order,db);
//                        }
//                    }
//                }
//            }
//        }

        // get addresses from order selected
        Order temp = new Order();
        String addresses = temp.getAddresses();
        addressText.setText(addresses);
    }

}

