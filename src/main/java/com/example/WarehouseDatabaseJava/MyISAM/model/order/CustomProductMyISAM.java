package com.example.WarehouseDatabaseJava.MyISAM.model.order;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam")
public class CustomProductMyISAM {
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
