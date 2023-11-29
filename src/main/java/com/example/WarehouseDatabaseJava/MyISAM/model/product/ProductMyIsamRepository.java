package com.example.WarehouseDatabaseJava.MyISAM.model.product;

import com.example.WarehouseDatabaseJava.InnoDB.model.product.Product;
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
public interface ProductMyIsamRepository extends JpaRepository <ProductMyISAM, Integer> {
    @Query(value = "SELECT check_product_exist_by_name(:product_name) AS result", nativeQuery = true)
    Boolean isProductExistByName(@Param("product_name") String productName);

    @Procedure("insert_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    int insertProduct(@Param("new_name") String productName, @Param("new_quantity") int quantity, @Param("new_price") double price, @Param("new_description") String description);

    @Procedure("add_product_quantity")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    int addProductQuantity(@Param("new_product_name") String productName, @Param("new_quantity") int quantity);

    @Query(value = "SELECT * FROM product_myisam", nativeQuery = true)
    List<ProductMyISAM> getAllProductsList();

    @Query(value = "UPDATE product_myisam AS p SET p.description = :desc WHERE p.id = :product_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void saveDescriptionForProduct(@Param("product_id") int productId, @Param("desc") String description);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<ProductMyISAM> searchProductNatural(@Param("search_str") String searchStr);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str IN NATURAL LANGUAGE MODE) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<ProductMyISAM> searchProductNaturalWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str IN BOOLEAN MODE)", nativeQuery = true)
    List<ProductMyISAM> searchProductBool(@Param("search_str") String searchStr);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str IN BOOLEAN MODE) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<ProductMyISAM> searchProductBoolWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str WITH QUERY EXPANSION)", nativeQuery = true)
    List<ProductMyISAM> searchProductExp(@Param("search_str") String searchStr);

    @Query (value = "SELECT * FROM product_myisam WHERE MATCH(name, description) AGAINST (:search_str WITH QUERY EXPANSION) AND (price >= :min_val AND price <= :max_val)", nativeQuery = true)
    List<ProductMyISAM> searchProductExpWithPriceRange(@Param("search_str") String searchStr, @Param("min_val") Double minRangePrice, @Param("max_val") Double maxRangePrice);

    @Query(value = "SELECT MIN(price) FROM product_myisam", nativeQuery = true)
    double findMinPriceValue();

    @Query(value = "SELECT MAX(price) FROM product_myisam", nativeQuery = true)
    double findMaxPriceValue();
}
