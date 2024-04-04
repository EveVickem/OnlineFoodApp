package com.example.onlinefoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputEditText edEmail, edPassword;
    //Initialize the Database Instance
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hooks
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        //Escape to the Dashboard Activity if the user is already loggedin
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            if(SharedPrefManager.getInstance(this).getRole().equals("admin")){
                startActivity(new Intent(this,AdminDashboardActivity.class));
            }else{
                startActivity(new Intent(this,UserDashboardActivity.class));
            }
            finish();
        }

        ProgressDialog progressDialog = new ProgressDialog(this);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the progress dialog
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //get the text from the input fields
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "One of the field is empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

                    //Checking is the user with that email exists
                    Query query = databaseReference.child("users").orderByChild("email").equalTo(email);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                //This means that the user with that email exists

                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    // loops through each attributes of a user from the id up to
                                    // the date but first lets begin with the password
                                    String getPassword=dataSnapshot.child("password").getValue(String.class);
                                    if(password.equals(getPassword)){
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                        // Now get the user id and the role and store them using the Shared Preference Manager
                                        String getId=dataSnapshot.child("id").getValue(String.class);
                                        String getRole=dataSnapshot.child("role").getValue(String.class);

                                        SharedPrefManager.getInstance(MainActivity.this).userLogin(getId,getRole);
                                        // The most exiting part. Route the logged in user to different activities once
                                        // they are loggedin based on their roles.
                                        //Make sure to have the USerDashboard and AdminDashboard created
                                        if(getRole.equals("admin")){
                                            startActivity(new Intent(MainActivity.this,AdminDashboardActivity.class));
                                            finish();
                                        }else {
                                            startActivity(new Intent(MainActivity.this,UserDashboardActivity.class));
                                            finish();
                                        }

                                        progressDialog.dismiss();
                                    }else{
                                        // If a wrong password was entered
                                        Toast.makeText(MainActivity.this, "Login Failed. Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }

                                }

                            }else{
                                // This means that the user with that email doesn't exist
                                Toast.makeText(MainActivity.this, "User doesn't exist. Kindly Register first to proceed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }
            }
        });
    }
}