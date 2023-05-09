package com.example.WarehouseDatabaseJava.model.order;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relation with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer;

    // One-to-Many relation with OrderProduct
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    List<OrderProduct> orderProductList = new ArrayList<>();

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

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }
}
