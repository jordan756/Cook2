package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
//import com.example.cook2.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cook cook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button changeStatus = findViewById((R.id.toggleButton));
        Button start_button = findViewById(R.id.start_button);
        Button end_button = findViewById(R.id.end_button);

        //String key = getIntent().getExtras().getString("key");
        cook = getIntent().getExtras().getParcelable("Cook");
        //HAVE TO DO THIS TO GET UPDATED COOK FROM EDIT MENU
        String key = cook.getKey();
        cook = Util.getCook(key,db);
        ArrayList<Order> orders = Util.getAllOrders(cook.getOrders(),db);
        String temp;
        if (cook.open) {
            temp = "Availibility: Open";
            changeStatus.setText(temp);
        } else {
            temp = "Availibility: CLosed";
            changeStatus.setText(temp);
        }



        System.out.println(cook.getFirstName() + cook.getEmail() + "AGAGAGA");
        ArrayList<String> arrayList;
        ArrayAdapter<String> adapter;

        ListView listView = (ListView) findViewById(R.id.listOrders);

        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);

        String[] items = {"Example Order 1 (When we get database, we can remove these)", "Example Order 2", "Example Order 3"};
       // arrayList = new ArrayList<>(Arrays.asList(items));
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
                            if (order.status.equals("accepted_cook")) {
                                order.updateStatus();
                                Util.setOrder(order,db);
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
    }

    public void editMenu(View v) {

        Intent i = new Intent(this, CookUpdateMenu.class);
        i.putExtra("Cook",cook);
        startActivity(i);
    }
}