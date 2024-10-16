package com.example.admin_ecommerce.Model;

public class CategoryModel {
    String Category ;
    String imageUrl;

    public CategoryModel() {
    }

    public CategoryModel(String category, String image) {
        Category = category;
        this.imageUrl = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
