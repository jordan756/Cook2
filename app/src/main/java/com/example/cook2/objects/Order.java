package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalTime;

public class Order implements Parcelable {
    //WILL NEED TO STORE CUSTOMER INFO TOO
    public Food food;
    public Customer customer;
   // public Cook cook;
    public LocalTime estimated_total_time;
    public String status;
    int orderID; //cooks # item sold
    //unaccepted_cook,accepted_cook,finished_cook,accepted_driver,accepted_customer

    //public Delivery driver
    Order(Food food, Customer customer, int id ) {
        this.food = food;
        this.customer = customer;
        //this.cook = cook;
        status = "unaccepted_cook";
        //TEMP: REPLACE WITH CALL TO DATABASE TO PREVENT OVERLAP
        //cook.amount_sold++;
        orderID = id;
    }

    public void updateStatus() {
        switch (status) {
            case "unaccepted_cook":
                status = "accepted_cook";
                break;
            case "accepted_cook":
                status = "finished_cook";
                break;
            case "finished_cook":
                status = "accepted_driver";
                break;
            case "accepted_driver":
                status = "accepted_customer";
                break;
        }
    }
    //returns string of values imporant for order
    public String summary() {
        return food.name + "  -  " + status + "  -  " + orderID;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Order(Parcel in) {
        food = in.readParcelable(Food.class.getClassLoader());
        customer = in.readParcelable(Customer.class.getClassLoader());
        //cook = in.readParcelable(Cook.class.getClassLoader());
        estimated_total_time = (LocalTime) in.readSerializable();
        status = in.readString();
        orderID = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(food, i);
        parcel.writeParcelable(customer, i);
       // parcel.writeParcelable(cook, i);
        parcel.writeSerializable(estimated_total_time);
        parcel.writeString(status);
        parcel.writeInt(orderID);
    }
}
