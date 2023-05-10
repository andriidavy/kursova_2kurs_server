package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Custom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relation with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    // One-to-Many relation with CustomProduct
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "custom")
    List<CustomProduct> customProductList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CustomProduct> getCustomProductList() {
        return customProductList;
    }

    public void setCustomProductList(List<CustomProduct> customProductList) {
        this.customProductList = customProductList;
    }
}
