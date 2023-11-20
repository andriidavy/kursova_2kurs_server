package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer.cart;

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
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Procedure("_add_to_cart_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void addProductToCart(@Param("new_customer_id") int customerId, @Param("new_product_id") int productId, @Param("quantity") int quantity);

    @Procedure("_remove_from_cart_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteProductFromCart(@Param("customer_id") int customerId, @Param("product_id") int productId);

    @Procedure("_clear_cart")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void clearCart(@Param("customer_id") int customerId);

    @Query(value = "SELECT new com.example.WarehouseDatabaseJava.dto.cart.CartProductDTO (p.id, p.name, cp.quantity) FROM CartProduct cp JOIN Cart c ON cp.cart.id = c.id JOIN Product AS p ON cp.product.id = p.id WHERE c.customer.id = :customer_id")
    List<CartProductDTO> getCartProductByCustomerId(@Param("customer_id") int customerId);
}
