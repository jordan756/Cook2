package com.example.cook2.objects;
import java.time.LocalTime; //https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
import java.util.ArrayList;

public class Food {
    //CHOSEN BY COOK
    private String name;
    private double cost; //2 decimals max
    private LocalTime estimatedCookTime; //input by cook, no data collected yet
    ArrayList<String> tags;//Vegan, Peanut-Free// chosen by cook

    //UPDATED BY DATABASE
    private LocalTime actualCookTime; //average of of this type of food
    private int amountSold;
    private double currentRating;
    private int numberOfRatings;

    //constructor for food
    Food(String name,double cost,LocalTime estimatedCookTime,ArrayList<String> tags) {
        this.name = name;
        this.cost = cost;
        this.estimatedCookTime = estimatedCookTime;
        this.tags = tags;
    }
    


}
