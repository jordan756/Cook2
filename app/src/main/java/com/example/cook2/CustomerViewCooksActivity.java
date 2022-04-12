package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CustomerViewCooksActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Customer customer;
    ArrayList<Cook> allCooks;
    ListView cooks;
    Button viewMenu;
    ArrayList<String> cookSummaries;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_cooks);
        customer = getIntent().getParcelableExtra("Customer");
        customer = Util.getCustomer(customer.getKey(),db);
        allCooks = Util.getAllCooks(db);
        cooks = findViewById(R.id.listCooks);
        viewMenu = findViewById(R.id.viewMenu);
        cookSummaries = new ArrayList<>();

        for (Cook cook : allCooks) {
            cookSummaries.add(cook.summary());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cookSummaries);
        cooks.setAdapter(adapter);

        cooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);
            }
        });
    }

    public void viewMenu(View view) {
        int position = cooks.getCheckedItemPosition();
        if (position == -1) {
            return;
        }

        Cook cook = allCooks.get(position);
        Intent i = new Intent(CustomerViewCooksActivity.this, CustomerViewMenu.class);
        i.putExtra("Customer", customer);
        i.putExtra("Cook", cook);
        startActivity(i);
        // finishAffinity();
    }
}