package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMyIsamRepository extends JpaRepository<CartMyISAM, Integer> {

    @Procedure("add_to_cart_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void addProductToCart(@Param("new_customer_id") int customerId, @Param("new_product_id") int productId, @Param("quantity") int quantity);

    @Procedure("remove_from_cart_product")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteProductFromCart(@Param("customer_id") int customerId, @Param("product_id") int productId);

    @Procedure("clear_cart")
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void clearCart(@Param("customer_id") int customerId);
}
