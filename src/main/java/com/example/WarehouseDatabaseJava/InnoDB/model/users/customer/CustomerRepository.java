package com.example.WarehouseDatabaseJava.InnoDB.model.users.customer;

import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "INSERT INTO customer (name, surname, email, password) VALUES (:name, :surname, :email, :password)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void insertCustomer(@Param("name") String name,
                        @Param("surname") String surname,
                        @Param("email") String email,
                        @Param("password") String password);

    @Query(value = "SELECT * FROM customer AS c WHERE c.id = LAST_INSERT_ID() AND c.email = :email", nativeQuery = true)
    Customer getLastInsertedCustomer(@Param ("email") String email);

    @Query(value = "SELECT * FROM customer AS c WHERE c.email = :email AND c.password = :password", nativeQuery = true)
    Customer loginCustomer(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM customer AS c WHERE c.id = :customer_id", nativeQuery = true)
    Customer getCustomerById(@Param("customer_id") int customer_id);
}
