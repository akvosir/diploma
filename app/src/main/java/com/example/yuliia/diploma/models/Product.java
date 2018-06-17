package com.example.yuliia.diploma.models;

public class Product {
    private int productId;
    private String productName;
    private int productPriceLow;
    private int productPriceHigh;
    private String productDescription;
    private String productImg;

    public Product(){}

    public Product(int productId, String productName, int productPriceLow, int productPriceHigh, String productDescription, String productImg) {
        this.productId = productId;
        this.productName = productName;
        this.productPriceLow = productPriceLow;
        this.productPriceHigh = productPriceHigh;
        this.productDescription = productDescription;
        this.productImg = productImg;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPriceLow() {
        return productPriceLow;
    }

    public int getProductPriceHigh() {
        return productPriceHigh;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductImg() {
        return productImg;
    }
}
