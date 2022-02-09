package com.example.cook2.objects;

public class Cook implements Person{
    private String firstName;
    private String lastName;
    private double currentRating;
    private int numberOfRatings;

    //Needs ArrayList with food Objects
    String address; //maybe break into multiple pieces

    public double getCurrentRating() {
        System.out.println("current rating:" + currentRating);
        return currentRating;
    }

}
