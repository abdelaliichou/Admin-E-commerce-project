package com.example.admin_ecommerce.Model;

public class CustomerModel {
    private String name, email, CustomerURI;

    public CustomerModel(String name, String email, String CustomerURI) {
        this.name = name;
        this.email = email;
        this.CustomerURI = CustomerURI;
    }

    public CustomerModel() {
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

    public void setCustomerURI(String customerURI) {
        CustomerURI = customerURI;
    }

    public String getCustomerURI() {
        return CustomerURI;
    }
}
