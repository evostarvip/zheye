package com.evostar.model;

public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String head_url;

    public User() {
    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.head_url = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return head_url;
    }

    public void setHeadUrl(String headUrl) {
        this.head_url = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
