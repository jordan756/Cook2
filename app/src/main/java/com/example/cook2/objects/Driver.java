package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

//import java.util.ArrayList;

public class Driver extends Person implements Parcelable {

    //private ArrayList<Order> Orders;

    public Driver(String firstName, String lastName, double currentRating, String phone, String address,String password,String email) {
        super.firstName = firstName;
        super.lastName = lastName;
        this.email = email;
        this.password = password;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
       // Orders = new ArrayList<>();
        key = email + password;
}
    protected Driver(Parcel in) {
        email = in.readString();
        password = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        key = in.readString();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        phoneNumber = in.readString();
        //add vehicle info
        //vehicleMake = in.readString();
        //vehicleColor = in.readString();
        //vehiclePlate = in.readString();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {return new Driver(in);
        }
        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(key);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString( phoneNumber);
        parcel.writeString(address);
        //add vehicle info
        //parcel.writeString(vehicleMake);
        //parcel.writeString(vehicleColor);
        //parcel.writeSting(vehiclePlate);
    }
}