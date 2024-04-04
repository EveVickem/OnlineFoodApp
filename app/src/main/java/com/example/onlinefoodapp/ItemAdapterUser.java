package com.example.onlinefoodapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapterUser extends RecyclerView.Adapter<ItemAdapterUser.MyViewHolder> {

    final ArrayList<ItemModel> productModels;
    final Context context;

    public ItemAdapterUser(ArrayList<ItemModel> productModels, Context context) {
        this.productModels = productModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdapterUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.single_product_card_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapterUser.MyViewHolder holder, int position) {

        ItemModel currentItem = productModels.get(position);
        holder.txtCategory.setText(currentItem.getCategory());

        Toast.makeText(context, "toast: "+currentItem.getName(), Toast.LENGTH_SHORT).show();

        // Concatenate the price integer value with the Kshs. tag
        String price = "Kshs. " + currentItem.getPrice() + ".00";
        holder.txtPrice.setText(price);

        // Use Picasso library to load the image
        Picasso.get().load(currentItem.getImage()).into(holder.imageView);
        holder.details.setOnClickListener(v->{
            Intent intent=new Intent(context, DetailsActivity.class);
            intent.putExtra("id",currentItem.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        MaterialTextView txtname, txtPrice, txtCategory;
        MaterialCardView details;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_product_image);
            txtname = itemView.findViewById(R.id.txt_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtCategory = itemView.findViewById(R.id.txt_category);
            details = itemView.findViewById(R.id.details);
        }
    }
}

