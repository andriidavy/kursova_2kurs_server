package com.example.WarehouseDatabaseJava.InnoDB.model.users.manager;

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
public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    @Procedure("_insert_manager")
    @Modifying
    int insertManager(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("email") String email,
                      @Param("password") String password,
                      @Param("rep_password") String repPassword);

    @Query(value = "DELETE m FROM manager AS m WHERE m.id = :manager_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteManagerById(@Param("manager_id") int managerId);

    @Query(value = "SELECT m.id FROM manager AS m WHERE m.email = :email AND m.password = :password", nativeQuery = true)
    int loginManager(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM manager AS m WHERE m.id = :manager_id", nativeQuery = true)
    Manager getManagerById(@Param("manager_id") int managerId);

    @Query(value = "SELECT * FROM manager", nativeQuery = true)
    List<Manager> getAllManagers();
}
