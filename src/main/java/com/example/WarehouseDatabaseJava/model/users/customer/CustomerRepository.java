package com.example.WarehouseDatabaseJava.model.users.customer;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
