package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.order.Custom;
import jakarta.persistence.*;

@Entity
public class CustomerCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relation with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //Many-to-One relation with Custom
    @ManyToOne
    @JoinColumn(name = "custom_id")
    private Custom custom;

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

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }
}
