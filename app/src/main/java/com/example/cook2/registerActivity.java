package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cook2.objects.Cook;
import com.example.cook2.ui.login.LoginActivity;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerToMain = findViewById(R.id.registerToMain);
        Cook testCook = new Cook();
        System.out.println(testCook.getFirstName() + " " + testCook.getLastName());
        registerToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this, MainActivity.class);
                //i.putExtra("testCook",testCook);
                startActivity(i);
            }

        });
    }
}