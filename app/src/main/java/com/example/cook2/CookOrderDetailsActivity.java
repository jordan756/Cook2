package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CookOrderDetailsActivity extends AppCompatActivity {
    String customerName, cookName, totalCost;
    TextView customerNameTextView, cookNameTextView, totalCostTextView;
    ListView listDetails;
    ArrayList<String> orderDetails;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_order_details);
        orderDetails = (ArrayList<String>) getIntent().getSerializableExtra("DetailsList");
        listDetails = findViewById(R.id.listDetails);
        cookNameTextView = findViewById(R.id.CODACookName);
        customerNameTextView = findViewById(R.id.CODACustomerName);
        totalCostTextView = findViewById(R.id.CODATotalCost);

        customerName = orderDetails.get(0);
        orderDetails.remove(0);
        cookName = orderDetails.get(0);
        orderDetails.remove(0);
        totalCost = orderDetails.get(0);
        orderDetails.remove(0);


        cookNameTextView.setText(cookName);
        customerNameTextView.setText(customerName);
        totalCostTextView.setText(totalCost);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orderDetails);
        listDetails.setAdapter(adapter);
    }

    public void CODAtoMain(View v) {
        finish();
    }
}
