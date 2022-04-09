package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    ArrayAdapter<String> adapter2;
    ListView driverOrdersList;
    ListView driverOrdersAcceptedList;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList2;
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

        orders = Util.getAllOrdersOpen(db);
        driverOrdersList = findViewById(R.id.listMenu);
        driverOrdersAcceptedList = findViewById(R.id.listOrderItems);
        arrayList = new ArrayList<>();
        for (Order x : orders) {
            arrayList.add(x.summary2());
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
    }


    public void startOrder(View view) {
        arrayList2 = new ArrayList<String>();
        for (int i = 0; i < driverOrdersList.getCount(); i++) {
            if (driverOrdersList.isItemChecked(i)) {
                String temp = driverOrdersList.getItemAtPosition(i).toString();
                // update status in database:
                String[] orderValues = temp.split("  -  ");
                String orderKey = orderValues[2];
                Order order = Util.getOrder(orderKey, db);
                order.updateStatus();
                Util.setOrder(order, db);


                // update list item status string
//                orderValues[1] = order.getStatus();
                orderValues[1] = "accepted_driver";
                String updatedTemp = TextUtils.join("  -  ", orderValues);
                arrayList2.add(updatedTemp);
            }
        }
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        driverOrdersAcceptedList.setAdapter(adapter2);
        driverOrdersAcceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });
    }

    public void endOrder(View view) {
        arrayList2 = new ArrayList<String>();
        for (int i = 0; i < driverOrdersAcceptedList.getCount(); i++) {
            if (driverOrdersAcceptedList.isItemChecked(i)) {
                String temp = driverOrdersAcceptedList.getItemAtPosition(i).toString();
                // update status in database:
                String[] orderValues = temp.split("  -  ");
                if (!orderValues[1].equals("accepted_driver")) {
                    return;
                }

                String orderKey = orderValues[2];
                Order order = Util.getOrder(orderKey, db);
                order.updateStatus();



                // Util.setOrder(order, db);
                Util.removeOrder(order,db);

                //NEEDS MORE TESTING

                // update list item status string
                // orderValues[1] = order.getStatus();
                orderValues[1] = "accepted_customer";
                String updatedTemp = TextUtils.join("  -  ", orderValues);
                arrayList2.add(updatedTemp);
                //arrayList
            }
        }
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        driverOrdersAcceptedList.setAdapter(adapter2);
        driverOrdersAcceptedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });
    }

    public void profileButtonEvent(View view) {
        Intent profileActivity = new Intent(getApplicationContext(), driverMainActivity.class);
        startActivity(profileActivity);
    }

    public void getAddresses(View view) {
        for (int i = 0; i < driverOrdersList.getCount(); i++) {
            if (driverOrdersList.isItemChecked(i)) {
                String temp = driverOrdersList.getItemAtPosition(i).toString();
                String[] orderValues = temp.split("  -  ");
                String addresses = (orderValues[3]);
                addressText.setText(addresses);
                return;
            }
        }

        for (int i = 0; i < driverOrdersAcceptedList.getCount(); i++) {
            if (driverOrdersAcceptedList.isItemChecked(i)) {
                String temp = driverOrdersAcceptedList.getItemAtPosition(i).toString();
                String[] orderValues = temp.split("  -  ");
                String addresses = (orderValues[3]);
                addressText.setText(addresses);
                return;
            }
        }
    }

}

