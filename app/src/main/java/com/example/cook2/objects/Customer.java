package com.example.cook2.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;

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
        key = email.concat(password);
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
        key = email.concat(password);
        // key = email + password;
    }

    public Customer() {
        Orders = new ArrayList<>();
    }

    /*
        myDoc.put("firstName", firstName);
        myDoc.put("lastName", lastName);
        myDoc.put("email", email);
        myDoc.put("password", password);
        myDoc.put("address", address);
        myDoc.put("phoneNumber", phoneNumber);
        myDoc.put("numberOfRatings", 0);
        myDoc.put("currentRating", 0);
        myDoc.put("userTypeKey", email.concat(password));
        myDoc.put("key", email.concat(password));
        myDoc.put("userType", "Customer");
     */

    public Customer(HashMap<String, String> myMap) {
        super.password = (String) myMap.get("password");
        super.email = (String) myMap.get("email");
        super.firstName = (String) myMap.get("firstName");
        super.lastName = (String) myMap.get("lastName");
        super.phoneNumber = (String) myMap.get("phoneNumber");
        super.address = (String) myMap.get("address");
        super.userType = (String) myMap.get("userType");
        super.userTypeKey = (String) myMap.get("userTypeKey");
        super.key = (String) myMap.get("key");
        super.currentRating = 0;
        super.numberOfRatings = 0;
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

        password = in.readString();
        email = in.readString();
        key = in.readString();

        Orders = in.readArrayList(Customer.class.getClassLoader());
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
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(key);
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
