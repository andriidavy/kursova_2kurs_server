package com.example.WarehouseDatabaseJava.model.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomProductRepository extends JpaRepository<CustomProduct, Integer> {
}
