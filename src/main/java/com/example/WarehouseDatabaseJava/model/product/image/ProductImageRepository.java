package com.example.WarehouseDatabaseJava.model.product.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
    boolean existsByProductId(String productId);

    ProductImage getReferenceByProductId(String productId);
}
