package com.example.WarehouseDatabaseJava.InnoDB.model.users.employee;

import com.example.WarehouseDatabaseJava.dto.users.EmployeeProfileDTO;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "INSERT INTO employee (name, surname, email, password) VALUES (:name, :surname, :email, :password)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void insertEmployee(@Param("name") String name,
                        @Param("surname") String surname,
                        @Param("email") String email,
                        @Param("password") String password);

    @Query(value = "DELETE FROM employee AS e WHERE e.id = :employee_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteEmployeeById(@Param("employee_id") int employeeId);

    @Query(value = "SELECT * FROM employee AS e WHERE e.id = LAST_INSERT_ID() AND e.email = :email", nativeQuery = true)
    Employee getLastInsertedEmployee(@Param ("email") String email);

    @Query(value = "SELECT * FROM employee AS e WHERE e.email = :email AND e.password = :password", nativeQuery = true)
    Employee loginEmployee(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM employee AS e WHERE e.id = :employee_id", nativeQuery = true)
    Employee getEmployeeById(@Param("employee_id") int employee_id);

    @Query(value = "SELECT (e.id, e.name, e.surname, e.email) FROM Employee AS e", nativeQuery = true)
    List<Employee> getAllEmployees();
}
