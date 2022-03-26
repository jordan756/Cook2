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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.View;


public class loginActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email, password, key;
    EditText emailInput;
    EditText passwordInput;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signIn(View v) {
        emailInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();
        key = email.concat(password);
        Log.d("login", "key => " + key);

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            emailInput.setError("Cannot be empty.");
            passwordInput.setError("Cannot be empty.");
        } else if (TextUtils.isEmpty(email)) {
            emailInput.setError("Cannot be empty.");
        } else if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Cannot be empty.");
        } else {
            db.collection("Person").whereEqualTo("email", email).whereEqualTo("password", password).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("login", document.getId() + " => " + document.getData());

                                    String userType = document.getString("userType");
                                    Person user = document.toObject(Person.class);
                                    if (userType.equals("Cook")) {
                                        Cook testCook = Util.getCook(user.getEmail() + user.getPassword(), db);
                                        Intent cookActivity = new Intent(loginActivity.this, MainActivity.class);
                                        cookActivity.putExtra("Cook", testCook);
                                        startActivity(cookActivity);


                                    } else if (userType.equals("Customer")) {
                                        Customer testCustomer = Util.getCustomer(user.getEmail() + user.getPassword(), db);
                                        Intent customerActivity = new Intent(loginActivity.this, CustomerMain.class);
                                        customerActivity.putExtra("Customer", testCustomer);
                                        startActivity(customerActivity);

                                    } else if (userType.equals("Driver")) {
                                        emailInput.setError("Not ready.");
                                        passwordInput.setError("Not ready.");
                                    }

                                    return;
                                }
                            } else {
                                Log.d("firebaseQuery", "Error getting documents.", task.getException());
                                emailInput.setError("Does not exist.");
                                passwordInput.setError("Does not exist.");
                                return;
                            }
                        }
                    });
        }
    }

    public void createAccount(View v) {
        Intent registerScreen = new Intent(v.getContext(), MainActivity4.class);
        startActivity(registerScreen);
    }
}