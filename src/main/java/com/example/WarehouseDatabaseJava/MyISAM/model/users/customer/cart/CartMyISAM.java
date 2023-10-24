package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam", name = "cart_myisam")
public class CartMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int customerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
