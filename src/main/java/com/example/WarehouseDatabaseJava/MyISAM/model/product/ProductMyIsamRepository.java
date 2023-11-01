package com.example.WarehouseDatabaseJava.MyISAM.model.product;

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

    @Query (value = "INSERT INTO product_myisam (name, quantity) VALUES (:product_name, :quantity)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    int insertProduct(@Param("product_name") String productName, @Param("quantity") int quantity);

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
}
