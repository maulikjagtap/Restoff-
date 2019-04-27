package com.example.design.restoff;

class Addpicmonth_Model {
    String shopname;
    String shopdescription;
    String url;

    public Addpicmonth_Model(String shopname, String shopdescription, String url) {
        this.shopname = shopname;
        this.shopdescription = shopdescription;
        this.url = url;
    }

    public Addpicmonth_Model() {
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopdescription() {
        return shopdescription;
    }

    public void setShopdescription(String shopdescription) {
        this.shopdescription = shopdescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
