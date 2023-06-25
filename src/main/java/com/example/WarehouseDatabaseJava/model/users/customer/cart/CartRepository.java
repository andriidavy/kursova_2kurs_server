package com.example.WarehouseDatabaseJava.model.users.customer.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Cart getReferenceByCustomer_Id(String customerId);
}
