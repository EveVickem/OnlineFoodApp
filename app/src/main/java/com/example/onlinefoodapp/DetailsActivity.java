package com.example.onlinefoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    String mealId;
    TextView txtName, txtCategory,txtDescription, textPrice, txtQuantity;


    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        mealId = intent.getStringExtra("id");

        txtQuantity = findViewById(R.id.quantity);
        txtName = findViewById(R.id.foodName);
        txtDescription= findViewById(R.id.description);
        textPrice= findViewById(R.id.price);
        txtCategory = findViewById(R.id.description);
        txtQuantity.setText(quantity + "");

        findViewById(R.id.add).setOnClickListener(v -> {
            quantity = quantity + 1;
            if (quantity >= 10) {
                quantity = 10;
            }
            txtQuantity.setText(quantity + "");
        });

        findViewById(R.id.minus).setOnClickListener(v -> {
            quantity = quantity - 1;
            if (quantity <= 0) {
                quantity = 1;
            }
            txtQuantity.setText(quantity + "");
        });

        MaterialButton cartButton = findViewById(R.id.addToCart);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity
                //  Query query=Constants.getDb().child("cart")
                if (SharedPrefManager.getInstance(DetailsActivity.this).isLoggedIn()){
                    CartModel cartModel = new CartModel(
                            SharedPrefManager.getInstance(DetailsActivity.this).getUserId(),
                            mealId,
                            quantity + "",
                    new Constants().getDate(),
                            false
                    );
                    Constants.getDb().child("cart").child(
                                    SharedPrefManager.getInstance(DetailsActivity.this).getUserId()
                            ).child(mealId).setValue(cartModel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(DetailsActivity.this, "Cart Updated", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{

                    Toast.makeText(DetailsActivity.this, "please login", Toast.LENGTH_SHORT).show();
                    // Start the login activity
                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

        });

        getMealDetails(mealId);

    }

    private void getMealDetails(String mealId) {
        Query query = Constants.getDb().child("products").orderByChild("id").equalTo(mealId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        ItemModel itemModel = productSnapshot.getValue(ItemModel.class);

                        txtName.setText(itemModel.getName());
                        txtDescription.setText(itemModel.getName());

                        txtCategory.setText(itemModel.getCategory());




                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailsActivity.this, "Failed to fetch", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

