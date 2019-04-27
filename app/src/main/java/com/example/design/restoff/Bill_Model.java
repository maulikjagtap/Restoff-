package com.example.design.restoff;

class Bill_Model {
    String nameshop;
    String shopamount;
    String about;
    float shoprating;
    String url;
    int status;

    public Bill_Model(String nameshop, String shopamount, String about, float shoprating, String url, int status) {
        this.nameshop = nameshop;
        this.shopamount = shopamount;
        this.about = about;
        this.shoprating = shoprating;
        this.url = url;
        this.status = status;
    }

    public Bill_Model() {
    }

    public String getNameshop() {
        return nameshop;
    }

    public void setNameshop(String nameshop) {
        this.nameshop = nameshop;
    }

    public String getShopamount() {
        return shopamount;
    }

    public void setShopamount(String shopamount) {
        this.shopamount = shopamount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public float getShoprating() {
        return shoprating;
    }

    public void setShoprating(float shoprating) {
        this.shoprating = shoprating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
