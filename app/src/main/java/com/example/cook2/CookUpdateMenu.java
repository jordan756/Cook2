package com.example.cook2;

import androidx.appcompat.app.AppCompatActivity;


import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Food;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CookUpdateMenu extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
   /*@Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_cook_update_menu);
   }*/
    private Cook cook;
    private ListView listMenu;
    private ArrayList<String> menuItems;
    private ArrayList<String> checkedPosition;
    private EditText dishName;
    private EditText dishCost;
    private EditText hour;
    private EditText min;
    private EditText tags;
    private Button createMenuButton;
    private Button deleteMenuButton;
    private ArrayAdapter<String> adapter;
    private SparseBooleanArray checkedPositions;
    private String getSelectedItemOfList;
    private ArrayList<String> temp;

    public void onCreate(Bundle icicle) {


        super.onCreate(icicle);
        setContentView(R.layout.activity_cook_update_menu);
        listMenu = (ListView) findViewById(R.id.listMenu);
        listMenu.setItemsCanFocus(false);
        menuItems = new ArrayList<String>();
        checkedPosition = new ArrayList<String>();
        dishName = (EditText) findViewById(R.id.editTextTextPersonName);
        dishCost = (EditText) findViewById(R.id.editTextTextPersonName2);
        hour = (EditText) findViewById(R.id.editTextTextPersonName3);
        min = (EditText) findViewById(R.id.editTextTextPersonName4);
        tags = (EditText) findViewById(R.id.editTextTextPersonName5);
        createMenuButton = (Button) findViewById(R.id.button6);
        createMenuButton.setEnabled(false);
        dishName.addTextChangedListener(watcher);
        dishCost.addTextChangedListener(watcher);
        hour.addTextChangedListener(watcher);
        min.addTextChangedListener(watcher);
        tags.addTextChangedListener(watcher);
        deleteMenuButton = (Button) findViewById(R.id.button14);

        //ADDED BY JORDAN

        cook = getIntent().getExtras().getParcelable("Cook");
        cook = Util.getCook(cook.getKey(),db);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);

        for (Food foodItem: cook.getMenu()) {
            String temp = "Dish: " + foodItem.name + "\n" + "Cost: " + foodItem.cost + "\n" + foodItem.estimatedCookTime.getHours() + "Hr " + foodItem.estimatedCookTime.getMinutes() + "Min" + "\n" + "Tags: " + foodItem.tags.get(0);
            menuItems.add(temp);
        }
        // ADDED BY JORDAN
        listMenu.setAdapter(adapter);



        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //view.setSelected(true);
                view.setBackgroundResource(R.drawable.select);

                for (int i = 0; i < listMenu.getCount(); i++) {

                    if (listMenu.isItemChecked(i)) {
                        String temp = listMenu.getItemAtPosition(i).toString();
                        checkedPosition.add(temp);
                    }

                    if (!listMenu.isItemChecked(i)) {
                        String temp = listMenu.getItemAtPosition(i).toString();
                        checkedPosition.remove(temp);
                    }
                }
            }
        });

        deleteMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < checkedPosition.size(); i++) {
                    adapter.remove(checkedPosition.get(i));
                    menuItems.remove(checkedPosition.get(i));
                    //convert string to object
                    String temp = checkedPosition.get(i);
                    String temp1 = temp.split("Dish: ")[1];
                    String name = temp1.split("\nCost: ",3)[0];
                    System.out.println(name);

                    for (Iterator<Food> it = cook.getMenu().iterator(); it.hasNext(); ) {
                        Food food = it.next();
                        if (food.name.equals(name)) {
                            it.remove();
                            break;
                        }
                    }


                }
                System.out.println(cook.getMenu());
                Util.setCook(cook,db);
                listMenu.setAdapter(adapter);
                checkedPosition.clear();
            }

        });
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}
        @Override
        public void afterTextChanged(Editable s) {
            if (dishName.getText().toString().length() == 0 || dishCost.getText().toString().length() == 0 || hour.getText().toString().length() == 0 || min.getText().toString().length() == 0 || tags.getText().toString().length() == 0) {
                createMenuButton.setEnabled(false);
            }
            else {
                createMenuButton.setEnabled(true);
            }
        }
    };

    public void createMenuItem(View v) {
        boolean duplicate = false;
        String temp = "Dish: " + dishName.getText().toString() + "\n" + "Cost: " + dishCost.getText().toString() + "\n" + hour.getText().toString() + "Hr " + min.getText().toString() + "Min" + "\n" + "Tags: " + tags.getText().toString();

        for (int i = 0; i < menuItems.size(); i++) {
            if ((temp.equals(menuItems.get(i)))) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            Toast.makeText(this, "No duplicate menu items", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Date time = new Date(0, 0, 0, Integer.parseInt(hour.getText().toString()), Integer.parseInt(min.getText().toString()), 0);
            ArrayList<String> tagsList = new ArrayList<String>();
            tagsList.add(tags.getText().toString());
            Food newMenuItem  = new Food(dishName.getText().toString(), Double.parseDouble(dishCost.getText().toString()), time, tagsList);
            cook.getMenu().add(newMenuItem);
            Util.setCook(cook,db);
          //  System.out.println(newMenuItem.name);
           // Util.createFood(newMenuItem, db);
            menuItems.add("Dish: " + dishName.getText().toString() + "\n" + "Cost: " + dishCost.getText().toString() + "\n" + hour.getText().toString() + "Hr " + min.getText().toString() + "Min" + "\n" + "Tags: " + tags.getText().toString());
        }

        adapter.notifyDataSetChanged();
    }
}

