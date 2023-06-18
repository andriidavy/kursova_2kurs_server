package com.example.WarehouseDatabaseJava.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getReferenceByBarcode(Long barcode);
    boolean existsByBarcode(Long barcode);
}
