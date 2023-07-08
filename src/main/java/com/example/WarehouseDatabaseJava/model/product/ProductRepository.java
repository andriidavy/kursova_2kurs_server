package com.example.WarehouseDatabaseJava.model.product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product getReferenceByBarcode(Long barcode);

    boolean existsByBarcode(Long barcode);

    boolean existsByProductCategory_Id(String categoryId);

    List<Product> findAllByProductCategory_Id(String categoryId);

    List<Product> findAllByProductCategory_Id(String categoryId, Sort sort);

    List<Product> findAllByNameContainingIgnoreCase(String productName);

    List<Product> findAllByNameContainingIgnoreCaseAndProductCategory_Id(String productName, String categoryId);

    //внутрішній метод отримання кількості входжень продукту в замовлення за останні два тижні (тут факт входження)
    @Query("SELECT COUNT(cp) FROM CustomProduct cp JOIN Custom c ON cp.custom.id = c.custom.id WHERE cp.product = :product AND c.creationTime > :twoWeeksAgo")
    int getProductOccurrencesWithinTwoWeeks(@Param("product") Product product, @Param("twoWeeksAgo") Date twoWeeksAgo);

    //внутрішній метод отримання числа всіх входжень продукту в замовлення за останні два тижні (тут сумма входжень)
    @Query("SELECT SUM(cp.quantity) FROM CustomProduct cp JOIN Custom c ON cp.custom.id = c.custom.id WHERE cp.product = :product AND c.createTime > :twoWeeksAgo")
    int getProductOccurrencesNumberWithinTwoWeeks(@Param("product") Product product, @Param("twoWeeksAgo") Date twoWeeksAgo);
}
