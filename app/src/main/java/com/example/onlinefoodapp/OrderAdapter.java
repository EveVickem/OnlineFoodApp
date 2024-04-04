package com.example.onlinefoodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderList;

    public OrderAdapter(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderId;
        private TextView tvOrderDetails;
        private TextView tvOrderStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvOrderDetails = itemView.findViewById(R.id.tv_order_details);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
        }

        public void bind(Order order) {
            tvOrderId.setText("Order ID: " + order.getOrderId());
            tvOrderDetails.setText("Items: " + order.getOrderDetails());
            tvOrderStatus.setText("Status: " + order.getOrderStatus());
        }
    }
}
