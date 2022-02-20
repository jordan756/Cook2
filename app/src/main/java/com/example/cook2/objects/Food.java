package com.example.cook2.objects;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalTime; //https://docs.oracle.com/javase/8/docs/api/java/time/LocalTime.html
import java.util.ArrayList;

public class Food implements Parcelable {
    //CHOSEN BY COOK
    public String name;
    public double cost; //2 decimals max
    public LocalTime estimatedCookTime; //input by cook, no data collected yet
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
    
    public Food() {
        //estimatedCookTime = new LocalTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Food(Parcel in) {
        name = in.readString();
        cost = in.readDouble();
        tags = in.createStringArrayList();
        amountSold = in.readInt();
        currentRating = in.readDouble();
        numberOfRatings = in.readInt();
        estimatedCookTime = (LocalTime) in.readSerializable();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(cost);
        parcel.writeStringList(tags);
        parcel.writeInt(amountSold);
        parcel.writeDouble(currentRating);
        parcel.writeInt(numberOfRatings);
        parcel.writeSerializable(estimatedCookTime);
    }
}
