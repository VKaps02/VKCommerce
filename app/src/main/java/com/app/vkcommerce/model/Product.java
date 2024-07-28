package com.app.vkcommerce.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Product {

    public static final String FIELD_ID = "id";
    public static final String FIELD_DOC_ID = "docId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_IMAGE_URI = "imageUri";
    public static final String FIELD_STOCK_QTY = "stockQty";
    public static final String FIELD_ORDER_QTY = "orderQty";
    public static final String FIELD_PRICE = "price";

    private String id;

    private String docId;
    private String name;
    private String imageUri;
    private int stockQty;
    private int orderQty;
    private double price;

    public Product() {}

    public Product(String id, String docId, String name, String imageUri, int stockQty, double price, int oderQty) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.stockQty = stockQty;
        this.price = price;
        this.orderQty = oderQty;
        this.docId = docId;
    }

    @NonNull
    @Override
    public String toString() {
        return"Product{" +
                "id='" + id + '\'' +
                ", docId='" + docId + '\'' +
                ", name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", stockQty=" + stockQty +
                ", orderQty=" + orderQty +
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

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }
}
