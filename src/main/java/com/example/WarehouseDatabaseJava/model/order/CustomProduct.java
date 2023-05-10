package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.product.Product;
import jakarta.persistence.*;

@Entity
public class CustomProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantity;
    // Many-to-One relation with Custom
    @ManyToOne
    @JoinColumn(name = "custom_id")
    Custom custom;

    // Many-to-One relation with Product
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Custom getOrder() {
        return custom;
    }

    public void setOrder(Custom custom) {
        this.custom = custom;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
