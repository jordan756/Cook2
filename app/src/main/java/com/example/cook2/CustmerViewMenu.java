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
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustmerViewMenu extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cook cook;
    Customer customer;
    ListView menuItems;
    ListView orderItems;
    Order order;
    ArrayList<String> orderDetails;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    TextView cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer_view_menu);

        // add = findViewById(R.id.addToOrder);
        //Button remove = findViewById(R.id.removeFromOrder);
        //Button createOrder = findViewById(R.id.createOrder);
        menuItems = (ListView) findViewById(R.id.listMenu);
        orderItems = (ListView) findViewById(R.id.listOrderItems);
        cost = findViewById(R.id.cost);





        cook = getIntent().getExtras().getParcelable("Cook");
        customer  = getIntent().getExtras().getParcelable("Customer");

        cook = Util.getCook(cook.getKey(),db);
        customer = Util.getCustomer(customer.getKey(),db);
        order = new Order(cook,customer);
        order.foods = new ArrayList<>();

        orderDetails = new ArrayList<>();
        ArrayList<String> menu = new ArrayList<>();
        for (Food temp : cook.getMenu()) {
            menu.add(temp.summary());
        }
        for (Food temp : order.foods) {
            orderDetails.add(temp.summary());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderDetails);

        menuItems.setAdapter(adapter);
        orderItems.setAdapter(adapter1);


        //menuItems.setOnItemClickListener(new );
    menuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            view.setSelected(true);
            view.setBackgroundResource(R.drawable.select);
        }
    });
    orderItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            view.setSelected(true);
            view.setBackgroundResource(R.drawable.select);
        }
    });
    }
    public void createOrder(View v) {
        if (order.foods.size() == 0) {
            return;
        }
        cook = Util.getCook(cook.getKey(),db);
       // customer = Util.getCustomer(customer.getKey(),db);

        order.status = "unaccepted_cook";

        order.orderKey = cook.getKey() + cook.amount_sold;
        cook.amount_sold = cook.amount_sold + 1;


        cook.getOrders().add(order.orderKey);
        customer.getOrders().add(order.orderKey);

        Util.setCook(cook,db);
        Util.setCustomer(customer,db);
        Util.setOrder(order,db);


        Intent i = new Intent(CustmerViewMenu.this, CustomerMain.class);
        i.putExtra("Customer",customer);
        startActivity(i);

        //Make sure

    }
    public void addToOrder(View v) {
        for (int i = 0; i < menuItems.getCount(); i++) {
            if (menuItems.isItemChecked(i)) {

                order.foods.add(cook.getMenu().get(i));
            }

        }
        //update costs
        orderDetails.clear();
        for (Food temp : order.foods) {
            orderDetails.add(temp.summary());
        }
        System.out.println(order.foods);
        orderItems.setAdapter(adapter1);
        String temp = "Total Cost: $" + order.totalCost();
        cost.setText(temp);
    }
    public void removeFromOrder(View v) {
        for (int i = 0; i < orderItems.getCount(); i++) {
            if (orderItems.isItemChecked(i)) {
                //might go out of bounds
                //orderDetails.remove(i);

                String temp = orderItems.getItemAtPosition(i).toString();
                String[] orderValues = temp.split(" \\$ ");

                String name = orderValues[0];
                System.out.println(name);
                Double cost = Double.parseDouble(orderValues[1]);
                for (Food food : order.foods) {
                    if (food.name.equals(name) && food.cost == cost) {
                        order.foods.remove(food);
                        break;
                    }
                }

            }
        }
        orderDetails.clear();
        for (Food temp : order.foods) {
            orderDetails.add(temp.summary());
        }
        orderItems.setAdapter(adapter1);
        String temp = "Total Cost: $" + order.totalCost();
        cost.setText(temp);
    }
}