package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO;
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
public interface CartMyIsamRepository extends JpaRepository<CartMyISAM, Integer> {

    @Procedure("add_to_cart_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void addProductToCart(@Param("new_customer_id") int customerId, @Param("new_product_id") int productId, @Param("quantity") int quantity);

    @Query(value = "DELETE cp FROM cart_product_myisam AS cp JOIN cart_myisam AS c ON c.id = cp.cart_id WHERE c.customer_id = :customer_id AND cp.product_id = :product_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteProductFromCart(@Param("customer_id") int customerId, @Param("product_id") int productId);

    @Query(value = "DELETE cp FROM cart_product_myisam AS cp JOIN cart_myisam AS c ON c.id = cp.cart_id WHERE c.customer_id = :customer_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void clearCart(@Param("customer_id") int customerId);

    @Query(value = "SELECT new com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO (p.id, p.name, cp.quantity) FROM CartProductMyISAM cp JOIN CartMyISAM c ON cp.cartId = c.id JOIN ProductMyISAM AS p ON cp.productId = p.id WHERE c.customerId = :customer_id")
    List<CartProductDTO> getCartProductByCustomerId(@Param("customer_id") int customerId);
}
