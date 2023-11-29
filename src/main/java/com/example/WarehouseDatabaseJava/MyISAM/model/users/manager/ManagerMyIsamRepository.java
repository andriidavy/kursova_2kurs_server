package com.example.WarehouseDatabaseJava.MyISAM.model.users.manager;

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
public interface ManagerMyIsamRepository extends JpaRepository<ManagerMyISAM, Integer> {
    @Procedure("insert_manager")
    @Modifying
    int insertManager(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("email") String email,
                      @Param("password") String password,
                      @Param("repPassword") String repPassword);

    @Query(value = "DELETE m FROM manager_myisam AS m WHERE m.id = :manager_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void deleteManagerById(@Param("manager_id") int managerId);

    @Query(value = "SELECT * FROM manager_myisam AS m WHERE m.email = :email AND m.password = :password", nativeQuery = true)
    ManagerMyISAM loginManager(@Param("email") String email, @Param("password") String password);

    @Query(value = "SELECT * FROM manager_myisam AS m WHERE m.id = :manager_id", nativeQuery = true)
    ManagerMyISAM getManagerById(@Param("manager_id") int managerId);

    @Query(value = "SELECT * FROM manager_myisam", nativeQuery = true)
    List<ManagerMyISAM> getAllManagers();
}
