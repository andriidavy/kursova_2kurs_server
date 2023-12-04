package com.example.WarehouseDatabaseJava.InnoDB.model.product;

import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT _check_product_exist_by_name(:product_name) AS result", nativeQuery = true)
    Boolean isProductExistByName(@Param("product_name") String productName);

    @Procedure("_insert_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    int insertProduct(@Param("new_name") String productName, @Param("new_quantity") int quantity, @Param("new_price") double price, @Param("new_description") String description);

    @Procedure("_add_product_quantity")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    int addProductQuantity(@Param("new_product_name") String productName, @Param("new_quantity") int quantity);

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Product> getAllProductsList();

    @Query(value = "UPDATE product AS p SET p.description = :desc WHERE p.id = :product_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void saveDescriptionForProduct(@Param("product_id") int productId, @Param("desc") String description);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Product> searchProductNatural(@Param("search_str") String searchStr);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str IN NATURAL LANGUAGE MODE) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<Product> searchProductNaturalWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str IN BOOLEAN MODE)", nativeQuery = true)
    List<Product> searchProductBool(@Param("search_str") String searchStr);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str IN BOOLEAN MODE) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<Product> searchProductBoolWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str WITH QUERY EXPANSION)", nativeQuery = true)
    List<Product> searchProductExp(@Param("search_str") String searchStr);

    @Query(value = "SELECT * FROM product WHERE MATCH(name, description) AGAINST (:search_str WITH QUERY EXPANSION) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<Product> searchProductExpWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query(value = "SELECT * FROM product AS p WHERE p.id = :product_id", nativeQuery = true)
    Product searchProductById(@Param("product_id") int productId);

    @Query(value = "SELECT MIN(price) FROM product", nativeQuery = true)
    double findMinPriceValue();

    @Query(value = "SELECT MAX(price) FROM product", nativeQuery = true)
    double findMaxPriceValue();
}
