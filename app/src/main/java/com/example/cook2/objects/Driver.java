package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;

public class Driver extends Person implements Parcelable {

    private ArrayList<String> orderIds;
    private String vehicleMake;
    private String vehicleColor;
    private String vehiclePlate;
    private String vehicleModel;

    public Driver() {
        orderIds = new ArrayList<>();
    }

    public Driver(String firstName, String lastName, double currentRating, String phone, String address,String password,String email) {
        super.firstName = firstName;
        super.lastName = lastName;
        super.email = email;
        super.password = password;
        super.currentRating = currentRating;
        super.phoneNumber = phone;
        super.address = address;
        super.key = email.concat(password);
        super.ratingsList = new ArrayList<>();
        vehicleMake = "test";
        vehicleColor = "test";
        vehiclePlate = "test";
        vehicleModel = "test";
        orderIds = new ArrayList<>();
    }

    public Driver(HashMap<String, String> myMap) {
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
        super.ratingsList = new ArrayList<>();
        orderIds = new ArrayList<>();
        vehicleMake = "test";
        vehicleColor = "test";
        vehiclePlate = "test";
        vehicleModel = "test";
    }

    public String getVehicleMake() {
        return vehicleMake;
    }
    public String getVehicleColor() {
        return vehicleColor;
    }
    public String getVehiclePlate() {
        return vehiclePlate;
    }
    public String getVehicleModel() {
        return vehicleModel;
    }
    public ArrayList<String> getOrderIds() {
        return orderIds;
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
        address = in.readString();

        orderIds = in.readArrayList(String.class.getClassLoader());
        ratingsList = in.readArrayList(Integer.class.getClassLoader());
        vehicleMake = in.readString();
        vehicleColor = in.readString();
        vehiclePlate = in.readString();
        vehicleModel = in.readString();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(key);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeString(phoneNumber);
        parcel.writeString(address);

        parcel.writeList(orderIds);
        parcel.writeList(ratingsList);
        parcel.writeString(vehicleMake);
        parcel.writeString(vehicleColor);
        parcel.writeString(vehiclePlate);
        parcel.writeString(vehicleModel);
    }
}