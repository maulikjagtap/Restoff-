package com.example.design.restoff;

class RestaurantMenuModel {
    String iteamcategory;

    public RestaurantMenuModel(String iteamcategory) {
        this.iteamcategory = iteamcategory;
    }

    public RestaurantMenuModel() {
    }

    public String getIteamcategory() {
        return iteamcategory;
    }

    public void setIteamcategory(String iteamcategory) {
        this.iteamcategory = iteamcategory;
    }
}
