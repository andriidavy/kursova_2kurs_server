package com.example.WarehouseDatabaseJava.MyISAM.model.order;

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
public interface CustomMyIsamRepository extends JpaRepository <CustomMyISAM, Integer> {

    @Query(value = "SELECT * FROM custom_myisam", nativeQuery = true)
    List<CustomMyISAM> getAllCustoms();

    @Procedure("create_custom")
    @Modifying
    int createCustom(@Param("new_customer_id") int customerId, @Param("new_department_id") int departmentId);


    @Procedure("assign_employee_to_custom")
    @Modifying
    void assignEmployeeToCustom(@Param("new_employee_id") int employeeId, @Param("new_custom_id") int customId);

    @Procedure("set_custom_sent")
    @Modifying
    void setCustomSent(@Param("custom_id") int customId);

}
