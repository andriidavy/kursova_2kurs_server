package com.example.WarehouseDatabaseJava.model.product.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    ProductCategory findByCategoryName(String categoryName);
}
