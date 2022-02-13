package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.cook2.objects.Cook;
import com.example.cook2.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton backButton = findViewById(R.id.floatingActionButton);


        Cook testCook = (Cook) getIntent().getParcelableExtra("testCook");

        System.out.println(testCook.getFirstName() + " " + testCook.getLastName());
        System.out.println("size of list" + testCook.menu.size());
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.print
                System.out.println("floating button");
            }
        });
    }
}