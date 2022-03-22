package com.example.cook2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.widget.Button;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Customer;
import com.example.cook2.objects.Person;
import com.example.cook2.objects.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.View;


public class loginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email, password;
    EditText emailInput;
    EditText passwordInput;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // Customer test = new Customer("test1");
       // Util.setCustomer(test,db);

    }

    public void signIn(View v) {
        System.out.println("here");
        emailInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            emailInput.setError("Cannot be empty.");
            passwordInput.setError("Cannot be empty.");
        } else if (TextUtils.isEmpty(email)) {
            emailInput.setError("Cannot be empty.");
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Cannot be empty.");
        } else {
            //--------------
            Log.d("firebaseQuery", "executing query: signIn(View v)");
            db.collection("Person").whereEqualTo("email", email).whereEqualTo("password", password).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String userType = document.getString("userType");
                                    Person user = document.toObject(Person.class);
                                    //String userType = user.getUserType();
                                    Log.d("firebaseQuery", document.getId() + " => " + document.getData());
//                                    Log.d("firebaseQuery", userType);
//                                    Log.d("firebaseQuery", user.getUserType());
                                        System.out.println(userType);
                                    if (userType.equals("Cook")) {
                                        //Cook cook = document.toObject(Cook.class);
                                        //String myUserType = cook.getUserType();
                                     //   Cook cook = Util.getCook(user.getEmail()+user.getPassword(), db);
                                        Cook testCook = Util.getCook(user.getEmail() + user.getPassword(), db);

                                        System.out.println("here1");
                                        Intent i = new Intent(loginActivity.this, MainActivity.class);
                                        i.putExtra("Cook",testCook);
                                        startActivity(i);


                                    } else if (userType.equals("Customer")) {
                                        Customer testCustomer = Util.getCustomer(user.getEmail() + user.getPassword(), db);

                                        System.out.println("here2");
                                        Intent i = new Intent(loginActivity.this, CustomerMain.class);
                                        i.putExtra("Customer",testCustomer);
                                        startActivity(i);

                                    } else if (userType.equals("Driver")) {

                                    }

                                    emailInput.setError("Try again.");
                                    passwordInput.setError("Try again.");
                                    return;
                                }
                            } else {
                                Log.w("firebaseQuery", "Error getting documents.", task.getException());
                                emailInput.setError("Try again.");
                                passwordInput.setError("Try again.");
                                return;
                            }
                        }
                    });

            emailInput.setError("Wrong username or password.");
            passwordInput.setError("Wrong username or password.");
            //------------
        }
    }

    public void createAccount(View v) {
        v.setEnabled(false);
    }
}