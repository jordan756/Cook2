package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomerMain extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        Button viewCooks = findViewById(R.id.viewCooks);
        customer = getIntent().getParcelableExtra("Customer");


        customer = Util.getCustomer(customer.getKey(),db);




        viewCooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerMain.this, CustomerViewCooksActivity.class);
                i.putExtra("Customer",customer);
                startActivity(i);
            }
        });
    }


}