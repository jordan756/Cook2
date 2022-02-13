package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Cook extends Person implements Parcelable {


    public ArrayList<Food> menu;
    public ArrayList<Order> currentOrders;

    public Cook(String firstName, String lastName, double currentRating, String phone, String address) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        menu = new ArrayList<>();
        currentOrders = new ArrayList<>();
    }
    //test cook constructor
    public Cook() {
        super.firstName = "Jordan";
        super.lastName = "the Third";
        super.currentRating = 5;
        super.phoneNumber = "1-696-6969";
        super.address = "at your mom's house";
        menu = new ArrayList<>();
        currentOrders = new ArrayList<>();
    }

    protected Cook(Parcel in) {
        currentOrders = in.readArrayList(Cook.class.getClassLoader());
        menu = in.readArrayList(Cook.class.getClassLoader());

        firstName = in.readString();
        lastName = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString(); //ma
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

        parcel.writeList(currentOrders);
        parcel.writeList(menu);

        parcel.writeString(firstName);
        //System.out.println(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address); //ma
    }
}
