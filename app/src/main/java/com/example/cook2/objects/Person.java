package com.example.cook2.objects;

public class Person {
    protected String firstName;
    protected String lastName;
    protected double currentRating;
    protected int numberOfRatings;
    protected String phoneNumber;
    protected String address; //maybe break into multiple pieces

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
}

