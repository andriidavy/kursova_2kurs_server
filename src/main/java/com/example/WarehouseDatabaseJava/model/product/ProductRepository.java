package com.example.WarehouseDatabaseJava.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getReferenceByBarcode(Long barcode);
    boolean existsByBarcode(Long barcode);
    List<Product> findAllByCategoryId(String categoryId);
}
