package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalTime;

public class Order implements Parcelable {
    //WILL NEED TO STORE CUSTOMER INFO TOO
    public Food food;
    public Customer customer;
    public Cook cook;
    public LocalTime estimated_total_time;
    //public Delivery driver
    Order(Food food, Customer customer, Cook cook ) {
        this.food = food;
        this.customer = customer;
        this.cook = cook;
    }

    protected Order(Parcel in) {
        food = in.readParcelable(Food.class.getClassLoader());
        customer = in.readParcelable(Customer.class.getClassLoader());
        cook = in.readParcelable(Cook.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(food, i);
        parcel.writeParcelable(customer, i);
        parcel.writeParcelable(cook, i);
    }
}
