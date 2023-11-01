package com.example.WarehouseDatabaseJava.MyISAM.model.order;

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
public interface CustomMyIsamRepository extends JpaRepository <CustomMyISAM, Integer> {

    @Query(value = "SELECT * FROM custom_myisam", nativeQuery = true)
    List<CustomMyISAM> getAllCustoms();

    @Query(value = "UPDATE custom_myisam AS c SET c.employee_id = :employee_id WHERE c.id = :custom_id", nativeQuery = true)
    @Modifying
    @QueryHints(value = @QueryHint(name = AvailableHints.HINT_FLUSH_MODE, value = "COMMIT"))
    void assignEmployeeToCustom(@Param("employee_id") int employeeId, @Param("custom_id") int customId);
}
