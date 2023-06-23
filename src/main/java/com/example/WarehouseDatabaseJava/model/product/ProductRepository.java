package com.example.WarehouseDatabaseJava.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getReferenceByBarcode(Long barcode);
    boolean existsByBarcode(Long barcode);
    boolean existsByCategoryId(String categoryId);
    List<Product> findAllByCategoryId(String categoryId);
    Product findByBarcode(long barcode);
    List<Product> findAllByNameContainingIgnoreCase(String productName);
    List<Product> findAllByNameContainingIgnoreCaseAndCategoryId(String productName, String categoryId);
}
