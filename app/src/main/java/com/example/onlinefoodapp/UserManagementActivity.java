package com.example.onlinefoodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class UserManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        // Initialize RecyclerView and layout manager
        recyclerViewUsers = findViewById(R.id.recycler_view_users);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        // Initialize user list and adapter
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);

        // Set adapter for RecyclerView
        recyclerViewUsers.setAdapter(userAdapter);

        // Add sample users (you would fetch these from a database or API)
        addSampleUsers();
    }

    private void addSampleUsers() {
        // Add sample users to the list
        userList.add(new User("John Doe", "john.doe@example.com", "Admin"));
        userList.add(new User("Jane Smith", "jane.smith@example.com", "User"));
        userList.add(new User("Michael Johnson", "michael.johnson@example.com", "User"));

        // Notify adapter about data changes
        userAdapter.notifyDataSetChanged();
    }
}