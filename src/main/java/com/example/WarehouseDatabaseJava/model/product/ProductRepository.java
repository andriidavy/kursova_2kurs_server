package com.example.WarehouseDatabaseJava.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getReferenceByBarcode(Long barcode);
    boolean existsByBarcode(Long barcode);
    boolean existsByProductCategory_Id(String categoryId);
    List<Product> findAllByProductCategory_Id(String categoryId);
    List<Product> findAllByNameContainingIgnoreCase(String productName);
    List<Product> findAllByNameContainingIgnoreCaseAndProductCategory_Id(String productName, String categoryId);
}
