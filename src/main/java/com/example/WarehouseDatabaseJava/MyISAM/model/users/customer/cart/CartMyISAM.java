package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam")
public class CartMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
