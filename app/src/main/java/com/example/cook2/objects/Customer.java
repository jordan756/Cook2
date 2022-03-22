package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Customer extends Person implements Parcelable {

    private ArrayList<Order> Orders;

    public Customer(String firstName, String lastName, double currentRating, String phone, String address,String password,String email) {
        this.password = password;
        this.email = email;
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        Orders = new ArrayList<>();
        key = email + password;
    }
    public Customer(String email) {
        this.password = "test1";
        this.email = email;
        super.firstName = "Customer";
        super.lastName = "ayaya";
        super.currentRating = 3;
        super.phoneNumber = "1-696-6969";
        super.address = "outside";
        Orders = new ArrayList<>();
        key = email + password;
    }
    public Customer() {
        Orders = new ArrayList<>();
    }

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }

    //MAY NEED TO ALSO HAVE THIS ON PARENT CLASS
    protected Customer(Parcel in) {
        Orders = in.createTypedArrayList(Order.CREATOR);

        firstName = in.readString();
        lastName = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString(); //ma
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(Orders);

        parcel.writeString(firstName);
        //System.out.println(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address); //ma
    }
}
