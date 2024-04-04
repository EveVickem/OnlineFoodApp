package com.example.onlinefoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDashboardActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    Button browse_and_order, place_orders, order_tracking, account_management, payments;

    RecyclerView recyclerView;
    ArrayList<ItemModel> itemList = new ArrayList<>();
    ItemAdapterUser itemAdapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recycler_manage_items);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        MaterialButton cartButton = findViewById(R.id.addToCart);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                Intent intent = new Intent(UserDashboardActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Initialize item list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapterUser(itemList,this);
        // Set adapter for RecyclerView
        recyclerView.setAdapter(itemAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }



    private void loadProducts() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        itemList.clear();
        databaseReference.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ItemModel itemModel = dataSnapshot.getValue(ItemModel.class);
                        itemList.add(itemModel);
                        itemAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(UserDashboardActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
            }
        });


    }


            @Override
            public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.mn_logout) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserDashboardActivity.this);
                    //title
                    alertDialogBuilder.setTitle("Confirm");
                    //message
                    alertDialogBuilder.setMessage("Are you sure you want to logout?");

                    alertDialogBuilder.setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPrefManager.getInstance(UserDashboardActivity.this).logOut();
                                    Toast.makeText(UserDashboardActivity.this, "Logged Out Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UserDashboardActivity.this,MainActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    // create the alertDialog
                    AlertDialog alertDialog=alertDialogBuilder.create();

                    //show the dialog
                    alertDialog.show();
                    return true;
                }else
                    return super.onOptionsItemSelected(item);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.admin_menu,menu);
                return super.onCreateOptionsMenu(menu);
            }
        }



