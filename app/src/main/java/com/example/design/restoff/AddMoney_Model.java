package com.example.design.restoff;

class AddMoney_Model {
    String shopname;
    float money;

    public AddMoney_Model(String shopname, float money) {
        this.shopname = shopname;
        this.money = money;
    }

    public AddMoney_Model() {
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
