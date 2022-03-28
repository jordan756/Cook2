package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Cook extends Person implements Parcelable {

    public boolean open;
    private ArrayList<Food> menu;
    //private ArrayList<Order> currentOrders;
    public int amount_sold;
    private ArrayList<String> orderIds;


    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setOrders(ArrayList<String> orders) {
        orderIds = orders;
    }

    public int getAmount_sold() {
        return amount_sold;
    }

    public ArrayList<String> getOrders() {
        return orderIds;
    }

    /*
    public Cook(DataSnapshot copy) {
        this.menu = copy.getmenu;
        this.amount_sold = copy.amount_sold;
        this.Orders = copy.Orders;
        this.email = copy.email;
        this.password = copy.password;
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.currentRating = copy.currentRating;
         this.numberOfRatings = copy.numberOfRatings;
         this.phoneNumber = copy.phoneNumber;
         this.address = copy.address;
    }
    *?
     */

    public Cook(String firstName, String lastName, double currentRating, String phone, String address,String password,String email) {
        super.password = password;
        super.email = email;
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        amount_sold = 0;
        menu = new ArrayList<>();
      //  currentOrders = new ArrayList<>();
        orderIds = new ArrayList<>();
        //docId = firstName+lastName;
        open = false;
        super.key = email.concat(password);
    }

    /*
        myDoc.put("firstName", firstName);
        myDoc.put("lastName", lastName);
        myDoc.put("email", email);
        myDoc.put("password", password);
        myDoc.put("address", address);
        myDoc.put("phoneNumber", phoneNumber);
        myDoc.put("userTypeKey", email.concat(password));
        myDoc.put("key", email.concat(password));
        myDoc.put("userType", "Cook");
     */

    public Cook (HashMap<String, String> myMap) {
        super.password = (String) myMap.get("password");
        super.email = (String) myMap.get("email");
        super.firstName = (String) myMap.get("firstName");
        super.lastName = (String) myMap.get("lastName");
        super.phoneNumber = (String) myMap.get("phoneNumber");
        super.address = (String) myMap.get("address");
        super.key = (String) myMap.get("key");
        super.userType = (String) myMap.get("userType");
        super.userTypeKey = (String) myMap.get("userTypeKey");
        super.currentRating = 0;
        super.numberOfRatings = 0;
        amount_sold = 0;
        menu = new ArrayList<>();
        orderIds = new ArrayList<>();
        open = false;
    }


    public Cook() {
        ArrayList<String> tags = new ArrayList();
        menu = new ArrayList<>();
        orderIds = new ArrayList<>();
    }
    //test cook constructor
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cook(String address) {
        super.firstName = "BOBB";
        super.lastName = "the Third";
        super.currentRating = 5;
        super.phoneNumber = "1-696-6969";
        super.address = address;
        menu = new ArrayList<>();
        super.email = "test";
        super.password = "test";
    //    currentOrders = new ArrayList<>();
        orderIds = new ArrayList<>();
        //ArrayList<String> tags = new ArrayList(); tags.add("nut-free"); tags.add("vegan");


        //currentOrders.add(order1);
        //currentOrders.add(order2);
        open = false;
        key = email + password;

    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Cook(Parcel in) {
        key = in.readString();
        email = in.readString();
        password = in.readString();
        //currentOrders = in.readArrayList(Cook.class.getClassLoader());
        menu = in.readArrayList(Cook.class.getClassLoader());
        orderIds = in.readArrayList(String.class.getClassLoader());
        firstName = in.readString();
        lastName = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString(); //ma
        amount_sold = in.readInt();
        open = in.readBoolean();
    }

    public static final Creator<Cook> CREATOR = new Creator<Cook>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(key);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeList(menu);
        parcel.writeList(orderIds);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address); //ma
        parcel.writeInt(amount_sold);
        parcel.writeBoolean(open);
    }
    public void print() {
        System.out.println("Menu " + menu);
        System.out.println("Orders " + orderIds);
        System.out.println("Amt sld " + amount_sold);
        System.out.println("Firstname " + firstName);
    }
    public void changeOpen() {
        open = !open;
    }

    public String summary() {
        return firstName + " " + address + " rating: " + currentRating + "/5";
    }
}
