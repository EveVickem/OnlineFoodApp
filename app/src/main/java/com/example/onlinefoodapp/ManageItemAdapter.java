package com.example.onlinefoodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageItemAdapter extends RecyclerView.Adapter<ManageItemAdapter.ManageItemViewHolder> {

    private List<Item> itemList;

    public ManageItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ManageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage_item, parent, false);
        return new ManageItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ManageItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;
        private TextView tvItemPrice;

        public ManageItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
            tvItemPrice = itemView.findViewById(R.id.tv_item_price);
        }

        public void bind(Item item) {
            tvItemName.setText(item.getName());
            tvItemPrice.setText("Price: " + item.getPrice());
        }
    }
}
