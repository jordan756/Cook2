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
    //public String orderId;
    public String cookKey;
    public String customerKey;
    public String orderKey;
    //WILL NEED TO STORE CUSTOMER INFO TOO
    public ArrayList<Food> foods;
    //public Customer customer;
   // public Cook cook;
    public Date estimated_total_time;
    public String status;

    //unaccepted_cook,accepted_cook,finished_cook,accepted_driver,accepted_customer

    //public Delivery driver
    public Order(Cook cook, Customer customer) {
        cookKey = cook.getKey();
        customerKey = customer.getKey();

        this.foods = new ArrayList<>();
       // foods.add(food);
        //this.customer = customer;
        //this.cook = cook;
        status = "unaccepted_cook";
        //TEMP: REPLACE WITH CALL TO DATABASE TO PREVENT OVERLAP
        //cook.amount_sold++;
      //  orderKey = cookKey + ;
    }
    public Order() {

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
    //returns string of values imporant for order
    public String summary() {
        System.out.println(foods);
        //String address = addresses();
        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey;
//        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey + "  -  " + address;
    }

    public String summary2() {
        System.out.println(foods);
        String address = addresses();
//        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey;
        return "# items: " + foods.size() + "  -  " + status + "  -  " + orderKey + "  -  " + address;
    }

    public String addresses() {
        return "Addresses: " + getAddresses();
    }

    public String getAddresses() {
        String address;
        address = getCookAddresses() + " " + getCustomerAddresses();
        return address;
    }

    public String getCookAddresses() {
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

    public String getCustomerAddresses() {
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

//    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status;
//    }


    @RequiresApi(api = Build.VERSION_CODES.O)

    protected Order(Parcel in) {
        foods = in.readArrayList(Food.class.getClassLoader());
     //   customer = in.readParcelable(Customer.class.getClassLoader());
        //cook = in.readParcelable(Cook.class.getClassLoader());
        estimated_total_time = (Date) in.readSerializable();
        status = in.readString();
        orderKey = in.readString();
        cookKey = in.readString();
        customerKey = in.readString();


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
      //  parcel.writeParcelable(customer, i);
       // parcel.writeParcelable(cook, i);
        parcel.writeSerializable(estimated_total_time);
        parcel.writeString(status);
        parcel.writeString(orderKey);
        parcel.writeString(cookKey);
        parcel.writeString(customerKey);
    }
    public String totalCost() {
        double sum = 0;
        for (Food food: foods) {
            sum += food.cost;
        }
        df.setRoundingMode(RoundingMode.UP);
        //System.out.println( Double.parseDouble(df.format(sum)));
        return df.format(sum);
    }
}
