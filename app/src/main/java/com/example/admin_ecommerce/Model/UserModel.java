package com.example.admin_ecommerce.Model;

public class UserModel {
    private String name , email , password ;
    private int phonenumber ;

    public UserModel(String name, String email, String password, int phonenumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;
    }

    public UserModel() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }
}
