package com.example.WarehouseDatabaseJava.model.order;

public class CustomProductDTO {

    private int customId;
    Custom.Status status;
    private int productId;
    private String productName;
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int id) {
        this.customId = id;
    }

    public Custom.Status getStatus() {
        return status;
    }

    public void setStatus(Custom.Status status) {
        this.status = status;
    }
}
