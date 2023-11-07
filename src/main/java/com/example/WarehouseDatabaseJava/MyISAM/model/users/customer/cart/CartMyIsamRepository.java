package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMyIsamRepository extends JpaRepository <CartMyISAM, Integer> {

    @Procedure("add_to_cart_product")
    @Modifying
    void addProductToCart(@Param("new_customer_id") int customerId, @Param("new_product_id") int productId, @Param("quantity") int quantity);

    @Query(value = "DELETE FROM cart_product_myisam AS cp JOIN cart_myisam AS c ON c.id = cp.cart_id WHERE c.customer_id = :customer_id AND cp.product_id = :product_id", nativeQuery = true)
    @Modifying
    void deleteProductFromCart(@Param("customer_id") int customerId, @Param("product_id") int productId);

    @Query(value = "DELETE FROM cart_product_myisam AS cp JOIN cart_myisam AS c ON c.id = cp.cart_id WHERE c.customer_id = :customer_id", nativeQuery = true)
    @Modifying
    void clearCart(@Param("customer_id") int customerId);
}
