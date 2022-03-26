package com.example.cook2.objects;

import android.os.Parcel;

public class Person  {
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected double currentRating;
    protected int numberOfRatings;
    protected String phoneNumber;
    protected String address; //maybe break into multiple pieces
    protected String key;
    protected String userType;
    protected String userTypeKey;

    public Person() {}



    public String getUserType() {
        return userType;
    }
    public String getKey() {
        return key;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public double getCurrentRating() {
        return currentRating;
    }
    public int getNumberOfRatings() {
        return numberOfRatings;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUserTypeKey() {
        return userTypeKey;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setCurrentRating(double currentRating) {
        this.currentRating = currentRating;
    }
    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setKey(String key) {
        this.key = key;
    }
}

