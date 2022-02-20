package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Customer extends Person implements Parcelable {

    public ArrayList<Order> currentOrders;
    public Customer() {
        super.firstName = "Customer";
        super.lastName = "ayaya";
        super.currentRating = 3;
        super.phoneNumber = "1-696-6969";
        super.address = "outside";
        currentOrders = new ArrayList<>();
    }
//MAY NEED TO ALSO HAVE THIS ON PARENT CLASS
    protected Customer(Parcel in) {
        currentOrders = in.createTypedArrayList(Order.CREATOR);

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
        parcel.writeTypedList(currentOrders);

        parcel.writeString(firstName);
        //System.out.println(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address); //ma
    }
}
