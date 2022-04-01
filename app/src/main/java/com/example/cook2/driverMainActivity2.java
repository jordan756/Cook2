package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

public class driverMainActivity2 extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_2);

        Button button1 = findViewById(R.id.addToOrder);
        Button button2 = findViewById(R.id.startOrder);
        Button button3 = findViewById(R.id.endOrder);
        Button button4 = findViewById(R.id.profileButton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.addToOrder:
                Intent intent = new Intent(getApplicationContext(), driverMainActivity3.class);
                startActivity(intent);
                break;
            case R.id.startOrder:
                Intent intent1 = new Intent(getApplicationContext(), driverMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.endButton:
                Intent intent2 = new Intent(getApplicationContext(), driverMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.profileButton:
                Intent intent3 = new Intent(getApplicationContext(), driverMainActivity.class);
                startActivity(intent3);
                break;
        }
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

        String cookAddress = "";
    }

}

