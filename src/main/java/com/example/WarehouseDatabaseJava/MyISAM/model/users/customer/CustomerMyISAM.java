package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer;

import jakarta.persistence.*;

@Entity
@Table(catalog = "warehouse_database_myisam")
public class CustomerMyISAM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
