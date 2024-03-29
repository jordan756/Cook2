package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends Person implements Parcelable {

    private ArrayList<String> Orders;
    public ArrayList<String> getOrders() {
        return Orders;
    }
    public void setOrders(ArrayList<String> orders) {
        Orders = orders;
    }

    public Customer() {
        Orders = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, double currentRating, String phone, String address, String password, String email) {
        super.password = password;
        super.email = email;
        super.firstName = firstName;
        super.lastName = lastName;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        super.key = email.concat(password);
        super.ratingsList = new ArrayList<>();
        Orders = new ArrayList<>();
    }

    public Customer(HashMap<String, String> myMap) {
        super.password = myMap.get("password");
        super.email = myMap.get("email");
        super.firstName = myMap.get("firstName");
        super.lastName = myMap.get("lastName");
        super.phoneNumber = myMap.get("phoneNumber");
        super.address = myMap.get("address");
        super.userType = myMap.get("userType");
        super.userTypeKey = myMap.get("userTypeKey");
        super.key = myMap.get("key");
        super.currentRating = 0;
        super.numberOfRatings = 0;
        super.ratingsList = new ArrayList<>();
        Orders = new ArrayList<>();
    }

    public Customer(String email) {
        super.password = "test1";
        super.email = email;
        super.firstName = "Customer";
        super.lastName = "ayaya";
        super.currentRating = 3;
        super.phoneNumber = "1-696-6969";
        super.address = "outside";
        super.key = email.concat(password);
        super.ratingsList = new ArrayList<>();
        Orders = new ArrayList<>();
    }


    protected Customer(Parcel in) {
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        key = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        address = in.readString();

        Orders = in.readArrayList(String.class.getClassLoader());
        ratingsList = in.readArrayList(Integer.class.getClassLoader());
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
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(key);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);

        parcel.writeList(Orders);
        parcel.writeList(ratingsList);
    }
}
