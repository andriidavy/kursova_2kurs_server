package com.example.WarehouseDatabaseJava.MyISAM.model.users.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMyIsamRepository extends JpaRepository<CustomerMyISAM, Integer> {
    @Procedure(value = "insert_customer")
    @Modifying
    int insertCustomer(@Param("name") String name,
                       @Param("surname") String surname,
                       @Param("email") String email,
                       @Param("password") String password,
                       @Param("repPassword") String repPassword);

    @Query(value = "SELECT * FROM customer_myisam AS c WHERE c.email = :email AND c.password = :password", nativeQuery = true)
    CustomerMyISAM loginCustomer(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM customer_myisam AS c WHERE c.id = :customer_id", nativeQuery = true)
    CustomerMyISAM getCustomerById(@Param("customer_id") int customer_id);
}
