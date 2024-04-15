package com.example.WarehouseDatabaseJava.model.users.employee;

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
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Procedure("_insert_employee")
    @Modifying
    int insertEmployee(@Param("name") String name,
                       @Param("surname") String surname,
                       @Param("email") String email,
                       @Param("password") String password,
                       @Param("rep_password") String repPassword);

    @Query(value = "DELETE FROM employee AS e WHERE e.id = :employee_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteEmployeeById(@Param("employee_id") int employeeId);

    @Query(value = "SELECT e.id FROM employee AS e WHERE e.email = :email AND e.password = :password", nativeQuery = true)
    int loginEmployee(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM employee AS e WHERE e.id = :employee_id", nativeQuery = true)
    Employee getEmployeeById(@Param("employee_id") int employee_id);

    @Query(value = "SELECT * FROM Employee", nativeQuery = true)
    List<Employee> getAllEmployees();
}
