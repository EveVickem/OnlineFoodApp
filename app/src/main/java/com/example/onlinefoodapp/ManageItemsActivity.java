package com.example.onlinefoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageItemsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ItemModel> itemList = new ArrayList<>();
    ItemAdapter itemAdapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        // Initialize RecyclerView and layout manager
        recyclerView = findViewById(R.id.recycler_manage_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      
        // Initialize item list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList,this);
        // Set adapter for RecyclerView
        recyclerView.setAdapter(itemAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }


    private void loadProducts() {
        ProgressDialog progressDialog=new ProgressDialog(this);
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
                Toast.makeText(ManageItemsActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }

}