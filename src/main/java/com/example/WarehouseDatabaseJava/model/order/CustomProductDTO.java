package com.example.WarehouseDatabaseJava.model.order;


public class CustomProductDTO {
    private long productBarcode;
    private String productName;
    private double productPrice;
    private int quantity;

    public long getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(long productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
