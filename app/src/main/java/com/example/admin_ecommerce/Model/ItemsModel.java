package com.example.admin_ecommerce.Model;

import com.google.firebase.database.PropertyName;

public class ItemsModel {
    String productName ;
    @PropertyName("Price")
    String Price ;
    String ImageUrl ;
    boolean Liked ;

    public ItemsModel(String productName, String Price, String imageUrl, boolean liked) {
        this.productName = productName;
        this.Price = Price;
        ImageUrl = imageUrl;
        Liked = liked;
    }

    public ItemsModel(String productName, String Price, String imageUrl) {
        this.productName = productName;
        this.Price = Price;
        ImageUrl = imageUrl;
    }

    public ItemsModel() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @PropertyName("Price")
    public String getPrice() {
        return Price;
    }

    @PropertyName("Price")
    public void setPrice(String price) {
        Price = price;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isLiked() {
        return Liked;
    }

    public void setLiked(boolean liked) {
        Liked = liked;
    }
}
