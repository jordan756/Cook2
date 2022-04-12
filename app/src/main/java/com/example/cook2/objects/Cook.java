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
    public int amount_sold;
    private ArrayList<String> orderIds;

    public ArrayList<Food> getMenu() {
        return menu;
    }
    public ArrayList<String> getOrders() {
        return orderIds;
    }
    public boolean getOpen(){
        return open;
    }
    public int getAmount_sold() {
        return amount_sold;
    }
    public void setOrders(ArrayList<String> orders) {
        orderIds = orders;
    }


    public Cook() {
        // what to do with tags?
        // ArrayList<String> tags = new ArrayList();
        menu = new ArrayList<>();
        orderIds = new ArrayList<>();
    }


    public Cook(String firstName, String lastName, double currentRating, String phone, String address,String password, String email) {
        super.password = password;
        super.email = email;
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        super.key = email.concat(password);
        amount_sold = 0;
        menu = new ArrayList<>();
        orderIds = new ArrayList<>();
        open = false;
    }


    public Cook(HashMap<String, String> myMap) {
        super.password = myMap.get("password");
        super.email = myMap.get("email");
        super.firstName = myMap.get("firstName");
        super.lastName = myMap.get("lastName");
        super.phoneNumber = myMap.get("phoneNumber");
        super.address = myMap.get("address");
        super.key = myMap.get("key");
        super.userType = myMap.get("userType");
        super.userTypeKey = myMap.get("userTypeKey");
        super.currentRating = 0;
        super.numberOfRatings = 0;
        amount_sold = 0;
        menu = new ArrayList<>();
        orderIds = new ArrayList<>();
        open = false;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cook(String address) {
        super.firstName = "BOBB";
        super.lastName = "the Third";
        super.currentRating = 5;
        super.phoneNumber = "1-696-6969";
        super.address = address;
        super.email = "test";
        super.password = "test";
        super.key = email + password;
        menu = new ArrayList<>();
        open = false;
        orderIds = new ArrayList<>();
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Cook(Parcel in) {
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        key = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString();

        // menu parcelable correct?
        menu = in.readArrayList(Cook.class.getClassLoader());
        orderIds = in.readArrayList(String.class.getClassLoader());
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
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(key);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);

        parcel.writeList(menu);
        parcel.writeList(orderIds);
        parcel.writeBoolean(open);
        parcel.writeInt(amount_sold);
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
