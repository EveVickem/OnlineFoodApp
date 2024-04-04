package com.example.onlinefoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        // Initialize RecyclerView and layout manager
        recyclerViewOrders = findViewById(R.id.recycler_view_orders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        // Initialize order list and adapter
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);

        // Set adapter for RecyclerView
        recyclerViewOrders.setAdapter(orderAdapter);

        // Add sample orders (you would fetch these from a database or API)
        addSampleOrders();
    }

    private void addSampleOrders() {
        // Add sample orders to the list
        orderList.add(new Order("123456", "Pizza, Burger, Fries", "Delivered"));
        orderList.add(new Order("789012", "Salad, Sandwich", "Pending"));
        orderList.add(new Order("345678", "Sushi, Ramen", "Cancelled"));

        // Notify adapter about data changes
        orderAdapter.notifyDataSetChanged();
    }
}