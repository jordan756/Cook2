package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class RateOrderActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView listDetails;
    ArrayList<String> orderDetails;
    ArrayAdapter<String> adapter;
    String userType, cookName, customerName, driverName, totalCost, customerKey, cookKey, driverKey;
    Button button;
    TextView customerNameTextView, cookNameTextView, driverNameTextView, totalCostTextView;
    EditText cookRatingET, driverRatingET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_order);
        button = findViewById(R.id.ROButton);
        listDetails = findViewById(R.id.ROListOrders);
        orderDetails = (ArrayList<String>) getIntent().getSerializableExtra("DetailsList");

        // ROCustomerName
        // ROCookName
        // RODriverName
        // ROTotalCost
        cookNameTextView = findViewById(R.id.ROCustomerName);
        customerNameTextView = findViewById(R.id.ROCookName);
        driverNameTextView = findViewById(R.id.RODriverName);
        totalCostTextView = findViewById(R.id.ROTotalCost);
        cookRatingET = findViewById(R.id.ROCookRatingText);
        driverRatingET = findViewById(R.id.RODriverRatingText);

        userType = orderDetails.get(0);
        orderDetails.remove(0);
        customerKey = orderDetails.get(0);
        orderDetails.remove(0);
        cookKey = orderDetails.get(0);
        orderDetails.remove(0);
        driverKey = orderDetails.get(0);
        orderDetails.remove(0);
        customerName = orderDetails.get(0);
        orderDetails.remove(0);
        cookName = orderDetails.get(0);
        orderDetails.remove(0);
        driverName = orderDetails.get(0);
        orderDetails.remove(0);
        totalCost = orderDetails.get(0);
        orderDetails.remove(0);

        customerNameTextView.setText(customerName);
        cookNameTextView.setText(cookName);
        driverNameTextView.setText(driverName);
        totalCostTextView.setText(totalCost);

        if (!userType.equals("Customer")) {
            String newText = "Back";
            button.setText(newText);
            cookRatingET.setEnabled(false);
            driverRatingET.setEnabled(false);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, orderDetails);
        listDetails.setAdapter(adapter);
    }


    public void customerSubmitRating(View v) {
        if (!userType.equals("Customer")) {
            finish();
            return;
        }


        String cookRating = cookRatingET.getText().toString();
        String driverRating = driverRatingET.getText().toString();


        int cookRatingInt;
        try {
            if (TextUtils.isEmpty(cookRating)) {
                cookRatingInt = -1;
                cookRatingET.setError("Must be int 1-5");
            } else {
                cookRatingInt = Integer.parseInt(cookRating);
                if (cookRatingInt > 5 | cookRatingInt <= 0) {
                    cookRatingInt = -1;
                    cookRatingET.setError("Must be int 1-5");
                }
            }

        } catch (NumberFormatException e) {
            cookRatingInt = -1;
            cookRatingET.setError("Must be int 1-5");
        }

        int driverRatingInt;
        try {
            if (TextUtils.isEmpty(driverRating)) {
                driverRatingInt = -1;
                driverRatingET.setError("Must be int 1-5");
            } else {
                driverRatingInt = Integer.parseInt(driverRating);
                if (driverRatingInt > 5 | driverRatingInt <= 0) {
                    driverRatingInt = -1;
                    driverRatingET.setError("Must be int 1-5");
                }
            }
        } catch (NumberFormatException e) {
            driverRatingInt = -1;
            driverRatingET.setError("Must be 1-5");
        }

        if (cookRatingInt != -1) {
            Cook cook = Util.getCook(cookKey, db);
            cook.newRating(cookRatingInt);
            Util.setCook(cook, db);
        }

        if (driverRatingInt != -1) {
            Driver driver = Util.getDriver(driverKey, db);
            driver.newRating(driverRatingInt);
            Util.setDriver(driver, db);
        }

        finish();
    }
}