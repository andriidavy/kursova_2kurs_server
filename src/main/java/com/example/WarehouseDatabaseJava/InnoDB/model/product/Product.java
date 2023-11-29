package com.example.WarehouseDatabaseJava.InnoDB.model.product;

import com.example.WarehouseDatabaseJava.InnoDB.model.order.CustomProduct;
import com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart.CartProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(catalog = "warehouse_database_innodb", name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;


    // One-to-Many relation with CartProduct
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<CartProduct> cartProductList = new ArrayList<>();

    // One-to-Many relation with CustomProduct

    @OneToMany(mappedBy = "product")
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
