package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam")
public class CartProductMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;

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
}
