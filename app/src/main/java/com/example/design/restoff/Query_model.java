package com.example.design.restoff;

class Query_model {
    String name;
    String email;
    String  query;

    public Query_model(String name, String email, String query) {
        this.name = name;
        this.email = email;
        this.query = query;
    }

    public Query_model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
