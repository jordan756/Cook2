package com.example.cook2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cook2.objects.Cook;
import com.example.cook2.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Button start_button = findViewById(R.id.start_button);
        Button end_button = findViewById(R.id.end_button);

        //FloatingActionButton backButton = findViewById(R.id.floatingActionButton);
        Cook cook = new Cook();
        ArrayList<String> arrayList;
        ArrayAdapter<String> adapter;

        ListView listView = (ListView) findViewById(R.id.listOrders);

        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);



        String[] items = {"Example Order 1 (When we get database, we can remove these)", "Example Order 2", "Example Order 3"};
        //arrayList = new ArrayList<>(Arrays.asList(items));
        arrayList = new ArrayList<>();
        arrayList.add(cook.currentOrders.get(0).food.name + " : " + cook.currentOrders.get(0).food.estimatedCookTime);
        arrayList.add(cook.currentOrders.get(1).food.name + " : " + cook.currentOrders.get(1).food.estimatedCookTime);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);

                System.out.println(position);
                //System.out.println("bruh");
            }
        });

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bruh");
            }
        });



        Cook testCook = (Cook) getIntent().getParcelableExtra("testCook");

        System.out.println(testCook.getFirstName() + " " + testCook.getLastName());
        System.out.println("size of list" + testCook.menu.size());
        /*backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //System.print
                System.out.println("floating button");
            }
        });*/
    }
}