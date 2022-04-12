package com.example.cook2.objects;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Parcelable {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public String cookKey;
    public String customerKey;
    public String driverKey;
    public String orderKey;
    public ArrayList<Food> foods;
    public Date estimated_total_time;
    public String status;


    public Order() {}


    public Order(Cook cook, Customer customer) {
        cookKey = cook.getKey();
        customerKey = customer.getKey();
        this.foods = new ArrayList<>();
        status = "unaccepted_cook";
        driverKey = "NA";
    }


    public void updateStatus() {
        switch (status) {
            case "unaccepted_cook":
                status = "accepted_cook";
                break;
            case "accepted_cook":
                status = "finished_cook";
                break;
            case "finished_cook":
                status = "accepted_driver";
                break;
            case "accepted_driver":
                status = "accepted_customer";
                break;
        }
    }


    public String summary() {
        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey;
    }


    public String summary2() {
        String address = addresses();
        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey + "  -  " + address;
    }


    public String addresses() {
        return "Addresses: " + getAddresses();
    }


    public String getAddresses() {
        String address;
        address = getCookAddress() + " " + getCustomerAddress();
        return address;
    }


    public String getCookAddress() {
        String address;
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Cook").document(cookKey).get();
        }

        while(!temp.isComplete()) {}

        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }

        Object temp3 = temp2.get("address");
        address = String.valueOf(temp3);
        return address;
    }


    public String getCustomerAddress() {
        String address;
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Customer").document(customerKey).get();
        }

        while(!temp.isComplete()) {}

        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }

        Object temp3 = temp2.get("address");
        address = String.valueOf(temp3);
        return address;
    }

    public String getDriverAddress() {
        if (driverKey.isEmpty()) {
            return null;
        }

        String address;
        Task<DocumentSnapshot> temp = null;
        while(temp == null) {
            temp = db.collection("Driver").document(driverKey).get();
        }

        while(!temp.isComplete()) {}

        DocumentSnapshot temp2 = temp.getResult();
        if (!temp2.exists()) {
            return null;
        }

        Object temp3 = temp2.get("address");
        address = String.valueOf(temp3);
        return address;
    }


    public String getCookKey() {
        return cookKey;
    }
    public void setCookKey(String cookKey) {
        this.cookKey = cookKey;
    }
    public String getCustomerKey() {
        return customerKey;
    }
    public void setCustomerKey(String customerKey) {
        this.customerKey = customerKey;
    }
    public String getOrderKey() {
        return orderKey;
    }
    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
    public ArrayList<Food> getFoods() {
        return foods;
    }
    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
    public Date getEstimated_total_time() {
        return estimated_total_time;
    }
    public void setEstimated_total_time(Date estimated_total_time) {
        this.estimated_total_time = estimated_total_time;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDriverKey() {
        return driverKey;
    }
    public void setDriverKey(String driverKey) {
        this.driverKey = driverKey;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected Order(Parcel in) {
        foods = in.readArrayList(Food.class.getClassLoader());
        estimated_total_time = (Date) in.readSerializable();
        status = in.readString();
        orderKey = in.readString();
        cookKey = in.readString();
        customerKey = in.readString();
        driverKey = in.readString();
    }


    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(foods);
        parcel.writeSerializable(estimated_total_time);
        parcel.writeString(status);
        parcel.writeString(orderKey);
        parcel.writeString(cookKey);
        parcel.writeString(customerKey);
        parcel.writeString(driverKey);
    }


    public String totalCost() {
        double sum = 0;
        for (Food food: foods) {
            sum += food.cost;
        }

        df.setRoundingMode(RoundingMode.UP);
        return df.format(sum);
    }
}
