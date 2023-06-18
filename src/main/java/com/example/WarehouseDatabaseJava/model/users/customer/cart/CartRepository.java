package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    @Modifying
    @Query("DELETE FROM CartProduct cp WHERE cp.cart = :cart")
    void deleteAllCartProductsByCart(@Param("cart") Cart cart);
}
