package com.example.WarehouseDatabaseJava.model.product;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findBySku(Long sku);
}
