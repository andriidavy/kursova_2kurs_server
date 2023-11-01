package com.example.WarehouseDatabaseJava.MyISAM.model.users.employee;

import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeMyIsamRepository extends JpaRepository<EmployeeMyISAM, Integer> {
    @Query(value = "INSERT INTO employee_myisam (name, surname, email, password) VALUES (:name, :surname, :email, :password)", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void insertEmployee(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("email") String email,
                      @Param("password") String password);

    @Query(value = "SELECT * FROM employee_myisam AS e WHERE e.id = LAST_INSERT_ID() AND e.email = :email", nativeQuery = true)
    EmployeeMyISAM getLastInsertedEmployee(@Param ("email") String email);

    @Query(value = "SELECT * FROM employee_myisam AS e WHERE e.email = :email AND e.password = :password", nativeQuery = true)
    EmployeeMyISAM loginEmployee(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM employee_myisam AS e WHERE e.id = :employee_id", nativeQuery = true)
    EmployeeMyISAM getEmployeeById(@Param("employee_id") int employee_id);
}
