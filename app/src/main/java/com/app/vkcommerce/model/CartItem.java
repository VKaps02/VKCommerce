package com.app.vkcommerce.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CartItem {
    public static final String FIELD_ID = "id";
    public static final String FIELD_ORDER_QTY = "orderQty";
    public static final String FIELD_DOC_ID = "docId";

    private String id;
    private String docId;
    private int orderQty;

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

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public CartItem() {}

    public CartItem(String id, String docId, int orderQty) {
        this.id = id;
        this.docId = docId;
        this.orderQty = orderQty;
    }

    @NonNull
    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", docId='" + docId + '\'' +
                ", orderQty=" + orderQty +
                '}';
    }

}
