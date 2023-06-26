package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double price;

    //One-to-One relation with Customer
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //One-to-Many relation with CartProduct
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProductList = new ArrayList<>();

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.round(price * 100.0) / 100.0;
    }

    public List<CartProduct> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }
}

