package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Cook cook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ArrayList<String> orderDetails = (ArrayList<String>) getIntent().getSerializableExtra("DetailsList");
        ListView listDetails = (ListView) findViewById(R.id.listDetails);
       // ListView listView = (ListView) findViewById(R.id.listOrders);
        //ArrayList<String> orderDetails = new ArrayList<>();
        ArrayAdapter<String> adapter;
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, orderDetails);

     //   cook = getIntent().getExtras().getParcelable("Cook");


        //HAVE TO DO THIS TO GET UPDATED COOK FROM EDIT MENU
     //   cook = Util.getCook(cook.getKey(),db);
        //System.out.println(cook.);
       // Food newFood = new Food("turkey", 9.00, new Date(0, 0, 0, 0, 35, 0), null);
       // cook.getOrders().get(0).foods.add(newFood);

       /* for (int i = 0; i < listView.getCount(); i++) {

            if (listView.isItemChecked(i)) {
                for (Food e : cook.getOrders().get(i).foods) {
                    orderDetails.add(e.name);
                }
            }
        }*/
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orderDetails);
        listDetails.setAdapter(adapter);
    }
}