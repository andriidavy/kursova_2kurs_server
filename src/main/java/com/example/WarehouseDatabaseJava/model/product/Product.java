package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.order.CustomProduct;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int quantity;

    // One-to-Many relation with CartProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    List<CartProduct> cartProductList = new ArrayList<>();

    // One-to-Many relation with CustomProduct

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    List<CustomProduct> customProductList = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }

    public List<CustomProduct> getCustomProductList() {
        return customProductList;
    }

    public void setCustomProductList(List<CustomProduct> customProductList) {
        this.customProductList = customProductList;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
