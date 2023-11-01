package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMyIsamRepository extends JpaRepository <CartMyISAM, Integer> {
}
