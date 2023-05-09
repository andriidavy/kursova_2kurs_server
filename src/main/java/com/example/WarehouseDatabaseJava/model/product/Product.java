package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.order.OrderProduct;
import com.example.WarehouseDatabaseJava.model.users.customer.cart.CartProduct;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    // One-to-Many relation with CartProduct
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartProduct> cartProductList = new ArrayList<>();

    // One-to-Many relation with OrderProduct

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderProduct> orderProductList = new ArrayList<>();

    public Product(){}
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
