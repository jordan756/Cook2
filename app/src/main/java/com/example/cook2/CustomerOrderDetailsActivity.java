package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomerOrderDetailsActivity extends AppCompatActivity {
    ListView listDetails;
    ArrayAdapter<String> adapter;
    ArrayList<String> orderDetails;
    String orderStatus, totalCost, cookName, cookAddress, driverName;
    TextView orderStatusTextView, totalCostTextView, cookNameTextView, cookAddressTV, driverNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_details);

        orderDetails = (ArrayList<String>) getIntent().getSerializableExtra("DetailsList");
        listDetails = (ListView) findViewById(R.id.listCustomerOrderDetails);

        // COD_OrderStatus
        // COD_TotalCost
        // COD_CookName
        // COD_CookAddress
        // COD_DriverName
        orderStatusTextView = findViewById(R.id.COD_OrderStatus);
        totalCostTextView = findViewById(R.id.COD_TotalCost);
        cookNameTextView = findViewById(R.id.COD_CookName);
        cookAddressTV = findViewById(R.id.COD_CookAddress);
        driverNameTV = findViewById(R.id.COD_DriverName);

        orderStatus = orderDetails.get(0);
        orderDetails.remove(0);
        totalCost = orderDetails.get(0);
        orderDetails.remove(0);
        cookName = orderDetails.get(0);
        orderDetails.remove(0);
        cookAddress = orderDetails.get(0);
        orderDetails.remove(0);
        driverName = orderDetails.get(0);
        orderDetails.remove(0);

        orderStatusTextView.setText(orderStatus);
        totalCostTextView.setText(totalCost);
        cookNameTextView.setText(cookName);
        cookAddressTV.setText(cookAddress);
        driverNameTV.setText(driverName);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orderDetails);
        listDetails.setAdapter(adapter);
    }

    public void CODtoCM(View view) {
        finish();
    }
}