package com.example.design.restoff;

class Stationarylist_model {
    String user_name;
    String user_email;
    String user_type;
    String user_address;
    String user_description;
    String user_contact;
    String uri;

    public Stationarylist_model(String user_name, String user_email, String user_type, String user_address, String user_description, String user_contact, String uri) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_type = user_type;
        this.user_address = user_address;
        this.user_description = user_description;
        this.user_contact = user_contact;
        this.uri = uri;
    }

    public Stationarylist_model() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_description() {
        return user_description;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }

    public String getUser_contact() {
        return user_contact;
    }

    public void setUser_contact(String user_contact) {
        this.user_contact = user_contact;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
