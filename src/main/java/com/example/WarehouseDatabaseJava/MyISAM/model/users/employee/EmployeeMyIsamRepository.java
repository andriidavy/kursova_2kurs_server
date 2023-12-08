package com.example.WarehouseDatabaseJava.MyISAM.model.users.employee;

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
public interface EmployeeMyIsamRepository extends JpaRepository<EmployeeMyISAM, Integer> {
    @Procedure("insert_employee")
    @Modifying
    int insertEmployee(@Param("name") String name,
                       @Param("surname") String surname,
                       @Param("email") String email,
                       @Param("password") String password,
                       @Param("repPassword") String repPassword);

    @Query(value = "DELETE FROM employee_myisam AS e WHERE e.id = :employee_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteEmployeeById(@Param("employee_id") int employeeId);

    @Query(value = "SELECT e.id FROM employee_myisam AS e WHERE e.email = :email AND e.password = :password", nativeQuery = true)
    int loginEmployee(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM employee_myisam AS e WHERE e.id = :employee_id", nativeQuery = true)
    EmployeeMyISAM getEmployeeById(@Param("employee_id") int employee_id);

    @Query(value = "SELECT * FROM employee_myisam", nativeQuery = true)
    List<EmployeeMyISAM> getAllEmployees();
}
