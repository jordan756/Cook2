package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Parcelable {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    //WILL NEED TO STORE CUSTOMER INFO TOO
    public ArrayList<Food> foods;
    //public Customer customer;
   // public Cook cook;
    public Date estimated_total_time;
    public String status;
    public int orderID; //cooks # item sold
    //unaccepted_cook,accepted_cook,finished_cook,accepted_driver,accepted_customer

    //public Delivery driver
    public Order(Food food, int id ) {
        this.foods = new ArrayList<>();
        foods.add(food);
        //this.customer = customer;
        //this.cook = cook;
        status = "unaccepted_cook";
        //TEMP: REPLACE WITH CALL TO DATABASE TO PREVENT OVERLAP
        //cook.amount_sold++;
        orderID = id;
    }
    public Order() {

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
        System.out.println(foods);
        return foods.get(0).name + "  -  " + status + "  -  " + orderID;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    protected Order(Parcel in) {
        foods = in.readArrayList(Food.class.getClassLoader());
     //   customer = in.readParcelable(Customer.class.getClassLoader());
        //cook = in.readParcelable(Cook.class.getClassLoader());
        estimated_total_time = (Date) in.readSerializable();
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
        parcel.writeList(foods);
      //  parcel.writeParcelable(customer, i);
       // parcel.writeParcelable(cook, i);
        parcel.writeSerializable(estimated_total_time);
        parcel.writeString(status);
        parcel.writeInt(orderID);
    }
    public double totalCost() {
        double sum = 0;
        for (Food food: foods) {
            sum += food.cost;
        }
        df.setRoundingMode(RoundingMode.UP);
        return Double.parseDouble(df.format(sum));
    }
}
