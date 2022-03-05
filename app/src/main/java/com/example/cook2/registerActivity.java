package com.example.cook2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Util;
import com.example.cook2.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;

public class registerActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerToMain = findViewById(R.id.registerToMain);


        Cook testCook = new Cook("useless");
        testCook.getMenu().add(new Food());
        testCook.setFirstName("Geourge");
        testCook.amount_sold = 3100;
        testCook.print();
        System.out.println("/????????");
        registerToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Cook cook = new Cook();
                // Add a new document with a generated ID
                Util.setCook(testCook,db);
                Intent i = new Intent(registerActivity.this, MainActivity.class);
                i.putExtra("address",testCook.getAddress());
                startActivity(i);
            }

        });
    }

}