package com.example.cook2.objects;

import java.util.ArrayList;

public class Cook extends Person{


    public ArrayList<Food> menu;
    public ArrayList<Order> currentOrders;

    Cook(String firstName, String lastName, double currentRating, String phone, String address) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        menu = new ArrayList<>();
        currentOrders = new ArrayList<>();
    }
    //test cook constructor
    Cook() {
        super.firstName = "Jordan";
        super.lastName = "the Third";
        super.currentRating = 5;
        super.phoneNumber = "1-696-6969";
        super.address = "at your mom's house";
        menu = new ArrayList<>();
        currentOrders = new ArrayList<>();
    }

}
