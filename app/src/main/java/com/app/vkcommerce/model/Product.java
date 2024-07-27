package com.app.vkcommerce.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_IMAGE_URI = "imageUri";
    public static final String FIELD_STOCK_QTY = "stockQty";
    public static final String FIELD_PRICE = "price";

    private String id;
    private String name;
    private String imageUri;
    private int stockQty;
    private double price;

    public Product() {}

    public Product(String id, String name, String imageUri, int stockQty, double price) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.stockQty = stockQty;
        this.price = price;
    }

    @NonNull
    @Override
    public String toString() {
        return"Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", stockQty=" + stockQty +
                ", price=" + price +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
