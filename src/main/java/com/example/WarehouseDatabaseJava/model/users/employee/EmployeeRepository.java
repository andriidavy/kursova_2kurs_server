package com.example.WarehouseDatabaseJava.model.users.employee;

import com.example.WarehouseDatabaseJava.model.users.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByEmail(String email);
}
