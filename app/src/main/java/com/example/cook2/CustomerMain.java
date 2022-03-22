package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerMain extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Customer customer = getIntent().getParcelableExtra("Customer");


        customer = Util.getCustomer(customer.getKey(),db);



    }
}