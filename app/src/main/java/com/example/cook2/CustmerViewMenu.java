package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custmer_view_menu);

        // add = findViewById(R.id.addToOrder);
        //Button remove = findViewById(R.id.removeFromOrder);
        //Button createOrder = findViewById(R.id.createOrder);
        ListView menu = (ListView) findViewById(R.id.listMenu);
        ListView orderItems = (ListView) findViewById(R.id.listOrderItems);

        ArrayAdapter<String> adapter;
        ArrayAdapter<String> adapter1;


        cook = getIntent().getExtras().getParcelable("Cook");
        customer  = getIntent().getExtras().getParcelable("Customer");

        cook = Util.getCook(cook.getKey(),db);
        customer = Util.getCustomer(customer.getKey(),db);
        Order order = new Order();
        order.foods = new ArrayList<>();


        ArrayList<String> Menu = new ArrayList<>();
        for (Food temp : cook.getMenu()) {
            Menu.add(temp.summary());
        }


    }
    public void createOrder(View v) {

    }
    public void addToOrder(View v) {

    }
    public void removeFromOrder(View v) {

    }
}