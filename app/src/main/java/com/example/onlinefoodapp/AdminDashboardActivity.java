package com.example.onlinefoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class AdminDashboardActivity extends AppCompatActivity {
    MaterialCardView manage_items, add_items, view_order, user_management;
    MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manage_items=findViewById(R.id.btn_manage_items);
        manage_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,ManageItemsActivity.class));
            }
        });
        add_items=findViewById(R.id.btn_add_item);
        add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,AddItemActivity.class));

            }
        });
        view_order=findViewById(R.id.btn_view_orders);
        view_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,ViewOrdersActivity.class));
            }
        });
        user_management=findViewById(R.id.btn_users);
        user_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this,UserManagementActivity.class));
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mn_logout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminDashboardActivity.this);
            //title
            alertDialogBuilder.setTitle("Confirm");
            //message
            alertDialogBuilder.setMessage("Are you sure you want to logout?");

            alertDialogBuilder.setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPrefManager.getInstance(AdminDashboardActivity.this).logOut();
                            Toast.makeText(AdminDashboardActivity.this, "Logged Out Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminDashboardActivity.this,MainActivity.class));
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
