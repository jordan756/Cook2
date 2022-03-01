package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Cook extends Person implements Parcelable {


    private ArrayList<Food> menu;
    //private ArrayList<Order> currentOrders;
    private int amount_sold;
    private ArrayList<Order> Orders;


    public ArrayList<Food> getMenu() {
        return menu;
    }



    public int getAmount_sold() {
        return amount_sold;
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }




    public Cook(String firstName, String lastName, double currentRating, String phone, String address) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        amount_sold = 0;
        menu = new ArrayList<>();
      //  currentOrders = new ArrayList<>();
        Orders = new ArrayList<>();
    }
    //test cook constructor
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cook() {
        super.firstName = "Jordan";
        super.lastName = "the Third";
        super.currentRating = 5;
        super.phoneNumber = "1-696-6969";
        super.address = "at your mom's house";
        menu = new ArrayList<>();
    //    currentOrders = new ArrayList<>();
        Orders = new ArrayList<>();
        ArrayList<String> tags = new ArrayList(); tags.add("nut-free"); tags.add("vegan");
        Food food1 = new Food("Pizza", 9.95, LocalTime.of(0,35), tags);
        Food food2 = new Food("hotdog", 2.00, LocalTime.of(0,7), tags);
        Food food3 = new Food("burger", 4.49, LocalTime.of(0,10), tags);
        menu.add(food1); menu.add(food2); menu.add(food3);

        Order order1 = new Order(food1,++amount_sold);
        Orders.add(order1);
        Order order2 = new Order(food2,++amount_sold);
        Orders.add(order2);
        Order order3 = new Order(food2,++amount_sold);
        Orders.add(order3);

        //currentOrders.add(order1);
        //currentOrders.add(order2);

    }



    protected Cook(Parcel in) {
        //currentOrders = in.readArrayList(Cook.class.getClassLoader());
        menu = in.readArrayList(Cook.class.getClassLoader());
        Orders = in.readArrayList(Cook.class.getClassLoader());
        firstName = in.readString();
        lastName = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString(); //ma
        amount_sold = in.readInt();
    }

    public static final Creator<Cook> CREATOR = new Creator<Cook>() {
        @Override
        public Cook createFromParcel(Parcel in) {
            return new Cook(in);
        }

        @Override
        public Cook[] newArray(int size) {
            return new Cook[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        //parcel.writeList(currentOrders);
        parcel.writeList(menu);
        parcel.writeList(Orders);
        parcel.writeString(firstName);
        //System.out.println(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address); //ma
        parcel.writeInt(amount_sold);
    }
}
