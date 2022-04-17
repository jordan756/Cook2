package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DriverOrderDetailsActivity extends AppCompatActivity {
    ListView listDetails;
    ArrayAdapter<String> adapter;
    ArrayList<String> orderDetails;
    String orderStatus, totalCost, cookName, cookAddress, driverName, customerName, customerAddress;
    TextView orderStatusTextView, totalCostTextView, cookNameTextView, cookAddressTV, driverNameTV, customerNameTextView, customerAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_order_details);
        orderDetails = (ArrayList<String>) getIntent().getSerializableExtra("DetailsList");
        listDetails = (ListView) findViewById(R.id.listDriverOrderDetails);


        orderStatusTextView = findViewById(R.id.DOD_OrderStatus);
        totalCostTextView = findViewById(R.id.DOD_TotalCost);
        driverNameTV = findViewById(R.id.DOD_DriverName);
        customerNameTextView = findViewById(R.id.DOD_CustomerName);
        customerAddressTextView = findViewById(R.id.DOD_CustomerAddress);
        cookNameTextView = findViewById(R.id.DOD_CookName);
        cookAddressTV = findViewById(R.id.DOD_CookAddress);


        orderStatus = orderDetails.get(0);
        orderDetails.remove(0);
        totalCost = orderDetails.get(0);
        orderDetails.remove(0);
        driverName = orderDetails.get(0);
        orderDetails.remove(0);
        customerName = orderDetails.get(0);
        orderDetails.remove(0);
        customerAddress = orderDetails.get(0);
        orderDetails.remove(0);
        cookName = orderDetails.get(0);
        orderDetails.remove(0);
        cookAddress = orderDetails.get(0);
        orderDetails.remove(0);


        orderStatusTextView.setText(orderStatus);
        totalCostTextView.setText(totalCost);
        driverNameTV.setText(driverName);
        customerNameTextView.setText(customerName);
        customerAddressTextView.setText(customerAddress);
        cookNameTextView.setText(cookName);
        cookAddressTV.setText(cookAddress);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orderDetails);
        listDetails.setAdapter(adapter);
    }

    public void DODtoMain(View v) {
        finish();
    }
}