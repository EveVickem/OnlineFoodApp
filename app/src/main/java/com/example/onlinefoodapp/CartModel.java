package com.example.onlinefoodapp;

public class CartModel {
    String userId,mealId,quantity,date;
    Boolean delivered;

    public CartModel() {
    }

    public CartModel(String userId, String mealId, String quantity, String date, Boolean approved) {
        this.userId = userId;
        this.mealId = mealId;
        this.quantity = quantity;
        this.date = date;
        this.delivered = approved;
    }

    public CartModel(String id, String userId, String mealId, String quantity, String date, Boolean approved) {
        this.userId = userId;
        this.mealId = mealId;
        this.quantity = quantity;
        this.date = date;
        this.delivered = approved;
    }


    public String getUserId() {
        return userId;
    }

    public String getMealId() {
        return mealId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public Boolean getDelivered() {
        return delivered;
    }
}
